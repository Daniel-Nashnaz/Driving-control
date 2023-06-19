package com.test.controller;

import com.test.dto.EcosystemTripDto;
import com.test.dto.EcosystemUserCountDTO;
import com.test.security.jwtService.CurrentUser;
import com.test.security.jwtService.UserDetailsImpl;
import com.test.service.implementation.EcosystemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ecology")
@RequiredArgsConstructor
public class EcosystemController {

    private final EcosystemServiceImpl ecosystemService;

    @GetMapping("/getAllEcologyByUserId/{userId}")
    public ResponseEntity<List<EcosystemUserCountDTO>> getAllEcologyByUserId(@PathVariable Integer userId) {

        return ResponseEntity.ok().body(ecosystemService.getAllEcologyByUser(userId));
    }

    @GetMapping("/getAllEcologyByTripId/{tripId}")
    public ResponseEntity<List<EcosystemTripDto>> getAllEcologyByTripId(@PathVariable Integer tripId) {
        return ResponseEntity.ok().body(ecosystemService.getAllEcologyByTrip(tripId));
    }

    @GetMapping("/getAllEcologyOfDriversByAdminId")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EcosystemUserCountDTO>> getAllEcologyOfDriversByAdminId(@CurrentUser UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(ecosystemService.getAllEcologyOfDriversByAdmin(userDetails.getId()));
    }
}
