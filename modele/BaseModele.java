/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.modele;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author user
 */
@MappedSuperclass
public abstract class BaseModele implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id")
        private Integer id ;

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        { 
            this.id = id;
        }
}
