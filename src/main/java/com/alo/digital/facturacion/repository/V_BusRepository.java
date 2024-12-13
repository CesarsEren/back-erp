package com.alo.digital.facturacion.repository;

import com.alo.digital.facturacion.entity.V_Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface V_BusRepository extends JpaRepository<V_Bus,String> {

/*    @Query("FROM V_Bus vb where vb.")
    public Optional<V_Bus> findV_BusByNroprogramacion();
*/

}
