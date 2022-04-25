package uz.agro.security.entity.abstractEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import uz.agro.security.entity.Status;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode
@MappedSuperclass
@Data
public class AbstractEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(strategy = "uuid",name = "uuid")
    private UUID id;

    @CreationTimestamp
    private Date createAt;

    @LastModifiedDate
    private Date updateAt;

    @Enumerated(EnumType.STRING )
    private Status status;

}
