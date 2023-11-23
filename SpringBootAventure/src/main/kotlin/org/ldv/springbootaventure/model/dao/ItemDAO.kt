import org.ldv.springbootaventure.model.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemDAO : JpaRepository<Item, Long> {
}
