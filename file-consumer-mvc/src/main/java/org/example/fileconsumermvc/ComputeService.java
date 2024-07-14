package org.example.fileconsumermvc;

import org.springframework.stereotype.Service;

@Service
public class ComputeService {
    public Long computeSize(byte[] fileContent) {
        return (long) fileContent.length;
    }
}
