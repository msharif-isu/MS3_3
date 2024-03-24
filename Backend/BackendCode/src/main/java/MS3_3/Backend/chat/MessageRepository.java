package MS3_3.Backend.chat;

import MS3_3.Backend.Groups.TravelGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>{
    List<Message> findAllByGroupId(int groupId);
}
