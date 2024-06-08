package grupo9.eduinovatte.domain.repository

import grupo9.eduinovatte.domain.model.Status
import org.springframework.data.jpa.repository.JpaRepository

interface StatusRepository : JpaRepository<Status, Int>  {
}