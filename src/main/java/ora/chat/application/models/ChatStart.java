package ora.chat.application.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ChatStart {

    @NotNull(message = "error.name.notnull")
    @NotBlank(message = "error.name.notblank")
    private String name;
    @NotNull(message = "error.message.notnull")
    @NotBlank(message = "error.message.notblank")
    private String message;
}
