package org.chimerax.prometheus.service.microservices;

import lombok.AllArgsConstructor;
import lombok.val;
import org.chimerax.prometheus.api.dto.CreateDocumentDTO;
import org.chimerax.prometheus.api.dto.RootFolderDTO;
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

    private static final String URL = "http://hades";
    private RestTemplate restTemplate;

    public String save(final CreateDocumentDTO document) {
        URI location = restTemplate.postForLocation(URL + "/documents", document);
        assert location != null;
        return location.toString();
    }

    public long saveRoot(final RootFolderDTO createFolderDTO) {
        URI location = restTemplate.postForLocation(URL + "/folders" +
                "/root", createFolderDTO);
        assert location != null;
        val loc = location.toString().split("/");
        return Long.parseLong(loc[loc.length - 1]);
    }
}
