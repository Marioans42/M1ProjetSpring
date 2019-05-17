/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.util;

import java.util.List;

/**
 *
 * @author user
 */
public class ResultatPage {

    public ResultatPage(List list , Integer nbPage){
        this.setList(list);
        this.setNbPage(nbPage);
    }
    
    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Integer getNbPage() {
        return nbPage;
    }

    public void setNbPage(Integer nbPage) {
        this.nbPage = nbPage;
    }
    private List list;
    private Integer nbPage;
}
