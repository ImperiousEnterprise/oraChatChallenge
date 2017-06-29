package ora.chat.application.models.wrapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ora.chat.application.models.Users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by MR.F on 7/1/2017.
 */
@Getter
@Builder
public class OutputMessages {
    private Long id;
    private Long chat_id;
    private Long user_id;
    private String message;
    private String create_at;
    private OutputResults.Data user;

    public static class OutputMessagesBuilder{
        public OutputMessagesBuilder create_at(Date data){
            this.create_at = formatDate(data);
            return this;
        }

        private String formatDate(Date d){
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            df.setTimeZone(tz);
            return df.format(d);
        }
    }

}
