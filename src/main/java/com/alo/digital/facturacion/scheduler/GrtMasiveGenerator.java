package com.alo.digital.facturacion.scheduler;

import com.alo.digital.facturacion.dto.SolicitudGrtDto;
import com.alo.digital.facturacion.entity.V_SolicitudGrt;
import com.alo.digital.facturacion.repository.V_SolicitudGrtRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GrtMasiveGenerator implements Task
{
    @Autowired
    V_SolicitudGrtRepository vSolicitudGrtRepository;

    @Override
    @Scheduled(cron = "*/10 * * * * *")
    public void start() {
        log.info("inicio de proceso");
        List<V_SolicitudGrt> solicitudGrtDtoList = vSolicitudGrtRepository.findSolicitudesGRT();
        solicitudGrtDtoList
                        .forEach(vSolicitudGrt -> {
                            vSolicitudGrt.setGenerado(2);
                            vSolicitudGrtRepository.save(vSolicitudGrt);
                        });
    }

}
