package org.eulu.predatorprey;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;

import java.util.List;

import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;
import static org.eulu.predatorprey.Constants.*;

public class TextFieldValidator {
    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");

    private final MFXTextField field;
    private final Label label;

    public TextFieldValidator(MFXTextField field, Label label) {
        this.field = field;
        this.label = label;
    }

    public void validate() {
        Constraint notEmptyConstraint = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage(LENGTH_ERR_MSG)
                .setCondition(field.textProperty().isNotEmpty())
                .get();

        Constraint digitConstraint = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage(DIGIT_ERR_MSG)
                .setCondition(Bindings.createBooleanBinding(
                        () -> containsAny(field.getText(), "", DIGITS),
                        field.textProperty()
                ))
                .get();

        this.field.getValidator()
                .constraint(notEmptyConstraint)
                .constraint(digitConstraint);

        this.field.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                label.setVisible(false);
                this.field.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
            }
        });

        this.field.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                List<Constraint> constraints = this.field.validate();
                if (!constraints.isEmpty()) {
                    this.field.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                    label.setText(constraints.getFirst().getMessage());
                    label.setVisible(true);
                }
            }
        });
    }
}
