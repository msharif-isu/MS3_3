package MS3_3.Backend.TravelGroupChat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByGroupId(int groupId);
}
