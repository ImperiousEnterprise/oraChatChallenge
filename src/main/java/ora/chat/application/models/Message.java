package ora.chat.application.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by zstaff on 2017/06/26.
 */
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "error.message.notnull")
    @NotBlank(message = "error.message.notblank")
    private String message;
    @JoinColumn(name = "user_id")
    @OneToOne
    private Users user;
    @JoinColumn(name="chat_id")
    @ManyToOne
    private Chat chat;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdat;


}
