package ora.chat.application.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name="chat")
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
    @JoinTable(name="users_chat", joinColumns=@JoinColumn(name="chat_id"), inverseJoinColumns=@JoinColumn(name="users_id"))
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Users> users = new ArrayList<Users>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "chat", orphanRemoval = true)
    private List<Message> messages = new ArrayList<Message>();

    public Message getLast_chat_message() {
        if(messages.size() == 1){
            return messages.get(messages.size()-1);
        }else {
            return messages.get(messages.size());
        }
    }

}
