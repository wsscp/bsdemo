/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */
package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.List;

/**
 * <p style="display:none">modifyRecord</p>
 *
 * @author QiuYangjun
 * @date 2013-12-12 下午12:03:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_RESOURCE")
public class Resources extends Base {
    private static final long serialVersionUID = 7731816023283852976L;

    private String parentId;
    private String name;
    private String uri;
    private String type;
    @Transient
    private String typeName;
    private String description;
    private Integer seq;
    @Transient
    private List<Resources> children;
    @Transient
    private Boolean hasChild;
    @Transient
    public Boolean getLeaf(){
    	if(hasChild==null){
    		return true;
    	}
    	return !hasChild;
    }
    public String getParentId() {
        return StringUtils.isBlank(this.parentId) ? WebConstants.ROOT_ID : this.parentId;
    }
    @Transient
    private Boolean roleQuery;
    @Transient
    private Boolean roleCreate;
    @Transient
    private Boolean roleDelete;
    @Transient
    private Boolean roleEdit;

}
