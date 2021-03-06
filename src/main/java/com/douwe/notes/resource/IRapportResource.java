package com.douwe.notes.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
@Path("/rapport")

public interface IRapportResource {

    @GET
    @Path(value = "pv/{niveauid : \\d+}/{optionid : \\d+}/{coursid : \\d+}/{anneeid : \\d+}/{session : \\d+}")
    @Produces("text/pdf")
    Response produirePv(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "coursid") long coursid, @PathParam(value = "anneeid") long anneeid, @PathParam(value = "session") int session);

    @GET
    @Path(value = "synthese/semestre/{niveauid : \\d+}/{optionid : \\d+}/{anneeid : \\d+}/{semestre : \\d+}")
    @Produces("text/pdf")
    Response produireSyntheseSemestrielle(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "anneeid") long anneeid, @PathParam(value = "semestre") long semestreid);

    @GET
    @Path(value = "synthese/annuelle/{niveauid : \\d+}/{optionid : \\d+}/{anneeid : \\d+}")
    @Produces("text/pdf")
    Response produireSyntheseAnnuelle(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "anneeid") long anneeid);

    @GET
    @Path(value = "relevet/{niveauid : \\d+}/{optionid : \\d+}/{anneeid : \\d+}")
    @Produces("text/pdf")
    Response produireRelevetParcours(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "anneeid") long anneeid);

    @GET
    @Path(value = "relevet/{niveauid : \\d+}/{optionid : \\d+}/{anneeid : \\d+}/{etudiantid : \\d+}")
    @Produces("text/pdf")
    Response produireRelevetEtudiant(@PathParam(value = "niveauid") long niveauid, @PathParam(value = "optionid") long optionid, @PathParam(value = "anneeid") long anneeid, @PathParam(value = "etudiantid") long etudiantid);
    
    
    @GET
    @Path(value = "diplomation/{cycle : \\d+}/{departement : \\d+}/{anneeid : \\d+}")
    @Produces("text/pdf")
    Response produireSyntheseDiplomation(@PathParam(value = "cycle") long cycleId, @PathParam(value = "departement") long departementId, @PathParam(value = "anneeid") long anneeId);
    
    @GET
    @Path(value = "synthese/annual")
    @Produces("text/pdf")
    Response produire();
}
