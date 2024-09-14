package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.entity.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StatusRepository : JpaRepository<Status, Int>  {
    @Query("SELECT s FROM Status s WHERE s.id = :id")
    fun findStatusById(id: Int): Status
}