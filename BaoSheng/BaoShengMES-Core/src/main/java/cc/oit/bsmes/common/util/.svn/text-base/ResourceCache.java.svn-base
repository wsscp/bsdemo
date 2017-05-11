package cc.oit.bsmes.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cc.oit.bsmes.bas.model.Org;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.bas.service.OrgService;
import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.fac.comparator.EquipOrderTaskLoadComparator;
import cc.oit.bsmes.fac.comparator.EquipWorkLoadComparator;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProductProcessService;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@Component
public class ResourceCache {

    @Resource
    private EquipInfoService equipInfoService;
    @Resource
    private OrgService orgService;
//    @Resource
//    private ProductCraftsService productCraftsService;
    @Resource
    private ProductProcessService productProcessService;
    @Resource
    private ProductService productService;
    @Resource
    private EquipCalendarService equipCalendarService;

    @Resource
    private EquipListService equipListService;


    private List<Org> orgs;
    private Multimap<String, EquipInfo> equipInfoOrgMap;
    private Multimap<String, EquipInfo> defaultEquipsMap;
    private Multimap<String, EquipInfo> processEquipsMap;
    private Map<String, EquipList> equipListMap;
//    private Map<String, ProductCrafts> craftsMap;
    private Multimap<String, ProductProcess> productProcessMap;
    private Multimap<String, ProductProcess> craftsProcessMap;
    private Map<String, Product> productMap;
    private List<Product> productList;
    private static Comparator<EquipInfo> equipWorkLoadComparator = new EquipWorkLoadComparator();
    private static Comparator<EquipInfo> equipOrderTaskLoadComparator = new EquipOrderTaskLoadComparator();

    public void init() {
        orgs = orgService.getAll();
        productList = productService.getAll();

//        craftsMap = new HashMap<String, ProductCrafts>();
        productMap = new HashMap<String, Product>();
        productProcessMap = ArrayListMultimap.create();
        craftsProcessMap = ArrayListMultimap.create();

        equipInfoOrgMap = ArrayListMultimap.create();
        equipListMap = new HashMap<String, EquipList>();
        defaultEquipsMap = ArrayListMultimap.create();
        processEquipsMap = ArrayListMultimap.create();

        //Map<String,ProductProcess> processMap = StaticDataCache.getProcessMap();
        for (Org org : orgs) {
            String orgCode = org.getOrgCode();
            Map<String, Object> param = new HashMap<String, Object>();
    		param.put("orgCode", orgCode);
            List<EquipInfo> equipInfos = equipInfoService.getEquipLine(param);
            for (EquipInfo equipInfo : equipInfos) {
                equipInfo.setEquipCalendar(equipCalendarService.getByEquipInfo(equipInfo, null, null));
            }
            equipInfoOrgMap.putAll(orgCode, equipInfos);

            for (EquipInfo equipInfo : equipInfos) {
                List<EquipList> equipLists = equipInfo.getEquipLists();
                if(equipLists == null){
                    equipLists = equipListService.getByEquipCode(equipInfo.getCode());
                }
                if(equipLists!=null&&equipLists.size()>0)
                {
                	  for (EquipList equipList : equipLists) {
                          String capacityKey = equipList.getProcessId()
                                  + BusinessConstants.CAPACITY_KEY_SEPARATOR
                                  + equipList.getEquipCode();
                          equipListMap.put(capacityKey, equipList);

                          if (equipList.getIsDefault() != null && equipList.getIsDefault()) {
                              defaultEquipsMap.put(equipList.getProcessId(), equipInfo);
                          }
                          ProductProcess process = StaticDataCache.getProcessByProcessId(equipList.getProcessId());
                          processEquipsMap.put(process.getProcessCode(), equipInfo);
                      }
                }

            }
        }
    }

    public List<Org> getOrgs() {
        if (orgs == null) {
            init();
        }
        return orgs;
    }

    public List<EquipInfo> getEquipInfoByOrgCode(String orgCode) {
        if (equipInfoOrgMap == null) {
            init();
        }
        return (List<EquipInfo>) equipInfoOrgMap.get(orgCode);
    }

    /**
     * 负载满的在前
     *
     * @param processCode
     * @param comparator  if 0 use equipWorkLoadComparator else use equipOrderTaskLoadComparator
     * @return
     * @author chanedi
     * @date 2014-2-27 下午4:32:31
     * @see
     */
    public List<EquipInfo> getProcessEquips(String processCode, int comparator) {
        if (processEquipsMap == null) {
            init();
        }
        List<EquipInfo> equipInfos = (List<EquipInfo>) processEquipsMap.get(processCode);
        sortEquipInfos(equipInfos, comparator);
        return equipInfos;
    }

    /**
     * 负载满的在前
     *
     * @param processId
     * @param comparator if 0 use equipWorkLoadComparator else use equipOrderTaskLoadComparator
     * @return equipCode list
     * @author chanedi
     * @date 2014年1月23日 下午6:07:16
     * @see
     */
    public List<EquipInfo> getDefaultEquips(String processId, int comparator) {
        if (defaultEquipsMap == null) {
            init();
        }
        List<EquipInfo> equipInfos = (List<EquipInfo>) defaultEquipsMap.get(processId);
        sortEquipInfos(equipInfos, comparator);
        return equipInfos;
    }

    public List<EquipInfo> getDefaultEquips(String processId) {
        if (defaultEquipsMap == null) {
            init();
        }
        return (List<EquipInfo>) defaultEquipsMap.get(processId);
    }

    public static void sortEquipInfos(List<EquipInfo> equipInfos, int comparator) {
        if (comparator == 0) {
            Collections.sort(equipInfos, equipWorkLoadComparator);
        } else {
            Collections.sort(equipInfos, equipOrderTaskLoadComparator);
        }
    }

    /**
     * @param processIdAndEquipCode 格式：processId;equipCode
     * @return
     * @author chanedi
     * @date 2014年1月23日 下午6:03:50
     * @see
     */
    public EquipList getEquipList(String processIdAndEquipCode) {
        if (equipListMap == null) {
            init();
        }
        return equipListMap.get(processIdAndEquipCode);
    }

   /* public ProductCrafts getCrafts(String craftId) {
        if (craftsMap == null) {
            init();
        }

        ProductCrafts productCrafts = craftsMap.get(craftId);
        if (productCrafts == null) {
            productCrafts = productCraftsService.getById(craftId);
            craftsMap.put(craftId, productCrafts);
        }
        return productCrafts;
    }
*/
    public List<ProductProcess> getProductProcessByProductCode(String productCode) {
        if (productProcessMap == null) {
            init();
        }

        List<ProductProcess> processes = (List<ProductProcess>) productProcessMap
                .get(productCode);
        if (processes == null || processes.size() == 0) {
            processes = productProcessService.getByProductCode(productCode);
            productProcessMap.putAll(productCode, processes);
        }
        return processes;
    }

    public List<ProductProcess> getProductProcessByCraftId(String craftsId) {
        if (craftsProcessMap == null) {
            init();
        }

        List<ProductProcess> processes = (List<ProductProcess>) craftsProcessMap
                .get(craftsId);
        if (processes == null || processes.size() == 0) {
            // 根据工艺ID 查询工序流程
            processes = productProcessService.getByProductCraftsId(craftsId);
            craftsProcessMap.putAll(craftsId, processes);
        }
        return processes;
    }

    public Product getProduct(String processId) {
        if (productMap == null) {
            init();
        }

        Product product = productMap.get(processId);
        if (product == null) {
            product = productService.getByProcessId(processId);
            productMap.put(processId, product);
        }
        return product;
    }

    public List<Product> getProductList() {
        if (productList == null) {
            init();
        }
        return productList;
    }

    public Product getProductByCode(String productCode) {
        if (getProductList() != null) {
            for (Product product : getProductList()) {
                if (StringUtils.equalsIgnoreCase(product.getProductCode(), productCode)) {
                    return product;
                }
            }
        } else if (getProductList() == null || getProductList().isEmpty()) {
            Product product = productService.getByProductCode(productCode);
            if (getProductList() == null) {
                productList = new ArrayList<Product>();
            }
            getProductList().add(product);
            return product;
        }
        return null;
    }

    public Multimap<String, EquipInfo> getProcessEquipsMap() {
        if (processEquipsMap == null) {
            init();
        }
        return processEquipsMap;
    }

}
