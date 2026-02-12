package ui.cli.builders;

import model.LinearSystem;
import ui.cli.basic.Builder;
import ui.cli.basic.Renderer;
import ui.cli.processor.UiProcessor;

public class ChangeSystemBuilder implements Builder<LinearSystem> {
    private final UiProcessor uiProcessor;
    private final Renderer<LinearSystem> linearSystemRenderer;

    public ChangeSystemBuilder(UiProcessor uiProcessor,
                               Renderer<LinearSystem> linearSystemRenderer) {
        this.uiProcessor = uiProcessor;
        this.linearSystemRenderer = linearSystemRenderer;
    }

    @Override
    public void build(LinearSystem linearSystem) {

    }
}
