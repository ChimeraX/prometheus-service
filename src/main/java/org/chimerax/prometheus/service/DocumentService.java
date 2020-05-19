package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.dto.CreateDocumentDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 13-May-20
 * Time: 4:47 PM
 */

@Service
@AllArgsConstructor
public class DocumentService {

    private RestTemplate restTemplate;

    public String save(final CreateDocumentDTO document) {
        URI location = restTemplate.postForLocation("http://hades/documents", document);
        assert location != null;
        return location.toString();
    }

}
