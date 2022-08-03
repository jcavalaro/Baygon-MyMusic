package com.ciandt.summit.bootcamp2022.domain.services;

import com.ciandt.summit.bootcamp2022.ApplicationConfigTest;
import com.ciandt.summit.bootcamp2022.domain.ports.repositories.PlaylistRepositoryPort;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlaylistServiceImplTest extends ApplicationConfigTest {

    @Mock
    PlaylistRepositoryPort playlistRepositoryPort;

    @InjectMocks
    PlaylistServiceImpl playlistServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
