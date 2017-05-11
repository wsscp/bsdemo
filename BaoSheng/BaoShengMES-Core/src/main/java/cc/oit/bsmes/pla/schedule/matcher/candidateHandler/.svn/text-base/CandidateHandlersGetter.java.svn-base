
package cc.oit.bsmes.pla.schedule.matcher.candidateHandler;

import cc.oit.bsmes.common.constants.ProcessCode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 羽霓 on 2014/4/14.
 */
@Component
public class CandidateHandlersGetter {

    private Map<String, ICandidatesHandler[]> candidatesMap;
    private ICandidatesHandler[] defaultHandlers;
    @Resource
    private ICandidatesHandler newWOMatCodeCandidatesHandler;
    @Resource
    private ICandidatesHandler sameWOMatCodeCandidatesHandler;
    @Resource
    private ICandidatesHandler specCandidatesHandler;
    @Resource
    private ICandidatesHandler colorCandidatesHandler;
    @Resource
    private ICandidatesHandler sizeCandidatesHandler;

    public void init() {
        candidatesMap = new HashMap<String, ICandidatesHandler[]>();
        defaultHandlers = new ICandidatesHandler[]{ newWOMatCodeCandidatesHandler };

        ICandidatesHandler[] extrusion = new ICandidatesHandler[]{ sameWOMatCodeCandidatesHandler, specCandidatesHandler, colorCandidatesHandler, sizeCandidatesHandler };
        //candidatesMap.put(ProcessCode.EXTRUSION_SINGLE.name(), extrusion);
        //candidatesMap.put(ProcessCode.EXTRUSION_DUAL.name(), extrusion);

        //extrusion = new ICandidatesHandler[]{ newWOMatCodeCandidatesHandler, specCandidatesHandler, colorCandidatesHandler, sizeCandidatesHandler };
        //candidatesMap.put(ProcessCode.JACKET_EXTRUSION.name(), extrusion);
    }

    public ICandidatesHandler[] getCandidatesHandlers(String processCode) {
        if (candidatesMap == null) {
            init();
        }

        ICandidatesHandler[] handlers = candidatesMap.get(processCode);
        if (handlers == null) {
            handlers = defaultHandlers;
        }

        return handlers;
    }

}
