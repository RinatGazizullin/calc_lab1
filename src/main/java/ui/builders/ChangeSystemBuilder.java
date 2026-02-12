package ui.builders;

import exceptions.DataException;
import model.LinearSystem;
import ui.basic.Builder;
import ui.basic.Renderer;
import ui.processor.UiProcessor;

import java.math.BigDecimal;

public class ChangeSystemBuilder implements Builder<LinearSystem> {
    private static final int MAX = 3;
    private final UiProcessor uiProcessor;
    private final Renderer<LinearSystem> linearSystemRenderer;

    public ChangeSystemBuilder(UiProcessor uiProcessor,
                               Renderer<LinearSystem> linearSystemRenderer) {
        this.uiProcessor = uiProcessor;
        this.linearSystemRenderer = linearSystemRenderer;
    }

    @Override
    public void build(LinearSystem linearSystem) throws DataException {
        uiProcessor.renderText(linearSystemRenderer.render(linearSystem),
                UiProcessor.Message.CLEAR_LAST, true);

        int x = -1, y = -1;
        BigDecimal value = BigDecimal.ZERO;
        boolean result = false;
        for (int i = 0; i < MAX; i++) {
            uiProcessor.renderText("Введите номер ЛУ - ", UiProcessor.Message.NEW_LINE, false);

            String s = uiProcessor.readString();
            try {
                x = Integer.parseInt(s) - 1;

                if (x < 0 || x >= linearSystem.getCoefficients().getSize()) {
                    uiProcessor.renderError(String.format("Выход за границы размерности:"
                            + " Число должно быть от %d до %d", 1,
                            linearSystem.getCoefficients().getSize()),
                            UiProcessor.Message.IGNORE_LAST);
                    continue;
                }
                result = true;
                break;
            } catch (NumberFormatException e) {
                uiProcessor.renderError(String.format("Значение <%s> должно быть целочисленным числом", s),
                        UiProcessor.Message.IGNORE_LAST);
            }
        }

        if (!result) {
            throw new DataException("Не удалось изменить коэффициент СЛУ");
        }

        result = false;
        for (int i = 0; i < MAX; i++) {
            uiProcessor.renderText("Введите номер коэффициента - ", UiProcessor.Message.NEW_LINE, false);

            String s = uiProcessor.readString();
            try {
                y = Integer.parseInt(s) - 1;

                if (y < 0 || y > linearSystem.getCoefficients().getSize()) {
                    uiProcessor.renderError(String.format("Выход за границы размерности:"
                                            + " Число должно быть от %d до %d", 1,
                                    linearSystem.getCoefficients().getSize() + 1),
                            UiProcessor.Message.IGNORE_LAST);
                    continue;
                }
                result = true;
                break;
            } catch (NumberFormatException e) {
                uiProcessor.renderError(String.format("Значение <%s> должно быть целочисленным числом", s),
                        UiProcessor.Message.IGNORE_LAST);
            }
        }

        if (!result) {
            throw new DataException("Не удалось изменить коэффициент СЛУ");
        }

        result = false;
        for (int i = 0; i < MAX; i++) {
            uiProcessor.renderText("Введите новое значение - ", UiProcessor.Message.NEW_LINE, false);

            String s = uiProcessor.readString();
            try {
                value = new BigDecimal(s);

                result = true;
                break;
            } catch (NumberFormatException e) {
                uiProcessor.renderText(String.format("Значение <%s> должно быть числом", s),
                        UiProcessor.Message.IGNORE_LAST, true);
            }
        }

        if (!result) {
            throw new DataException("Не удалось изменить коэффициент СЛУ");
        }

        if (y == linearSystem.getCoefficients().getSize()) {
            linearSystem.getConstants().getVector()[x] = value;
        } else {
            linearSystem.getCoefficients().getMatrix()[x][y] = value;
        }
        linearSystem.resetSolution();
    }
}
