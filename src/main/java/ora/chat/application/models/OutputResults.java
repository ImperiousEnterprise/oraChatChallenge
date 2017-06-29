package ora.chat.application.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * Created by MR.F on 6/30/2017.
 */
@Getter
@Setter
public class OutputResults {

    private HashMap<String, String> meta;
    private Data data;

    public OutputResults(Long id, String name, String email){
        data = new Data(Math.toIntExact(id),name,email);
        meta = new HashMap<String, String>();
    }

    @Getter
    public class Data {
        private int id;
        private String name;
        private String email;

        public Data(int id, String name, String email){
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }
}
