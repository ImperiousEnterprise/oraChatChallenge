package ora.chat.application.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name="Chat")
@Getter
@Setter
public class Chat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "users")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Users> users = new ArrayList<Users>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "chat", orphanRemoval = true)
    private List<Message> messages = new ArrayList<Message>();
    @ManyToOne
    @JoinFormula("(SELECT id FROM ChatMessages ORDER BY created_at DESC LIMIT 1)")
    private Message last_chat_message;

    public void setLast_chat_message() {
        this.last_chat_message = messages.get(messages.size()-1);
    }

}
