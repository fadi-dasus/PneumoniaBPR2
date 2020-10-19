//package com.bachelor;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertFalse;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.bachelor.dao.ImageRepository;
//import com.bachelor.model.Image;
//
////@SpringBootTest
//@RunWith(SpringRunner.class)
//@DataJpaTest
//class BachelorApplicationTests {
//	
//
//    @Autowired
//    private TestEntityManager entityManager;
// 
//    @Autowired
//    private ImageRepository imageRepository;
//
//    @Test
//    public void whenFindById_thenReturnImage() {
//        // given
//        Image img = new Image("path", "Normal");
//        entityManager.persist(img);
//
//     
//        // when
//        Image found = imageRepository.findByPhysicalPath(img.getPhysicalPath());
//        
//        assertThat(found.getPhysicalPath())
//        .isEqualTo(img.getPhysicalPath());
//  
//    }
//
//}
