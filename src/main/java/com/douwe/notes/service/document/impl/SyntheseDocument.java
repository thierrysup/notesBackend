package com.douwe.notes.service.document.impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.notes.dao.IAnneeAcademiqueDao;
import com.douwe.notes.dao.ICoursDao;
import com.douwe.notes.dao.ICreditDao;
import com.douwe.notes.dao.IDepartementDao;
import com.douwe.notes.dao.IEnseignantDao;
import com.douwe.notes.dao.IEtudiantDao;
import com.douwe.notes.dao.IEvaluationDao;
import com.douwe.notes.dao.INiveauDao;
import com.douwe.notes.dao.IOptionDao;
import com.douwe.notes.dao.IParcoursDao;
import com.douwe.notes.dao.IProgrammeDao;
import com.douwe.notes.dao.ISemestreDao;
import com.douwe.notes.dao.IUniteEnseignementDao;
import com.douwe.notes.entities.AnneeAcademique;
import com.douwe.notes.entities.Etudiant;
import com.douwe.notes.entities.Niveau;
import com.douwe.notes.entities.Option;
import com.douwe.notes.entities.Semestre;
import com.douwe.notes.entities.UniteEnseignement;
import com.douwe.notes.projection.MoyenneUniteEnseignement;
import com.douwe.notes.projection.UEnseignementCredit;
import com.douwe.notes.service.INoteService;
import com.douwe.notes.service.ServiceException;
import com.douwe.notes.service.document.ISyntheseDocument;
import com.douwe.notes.service.impl.DocumentServiceImpl;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class SyntheseDocument implements ISyntheseDocument {

    @EJB
    private INoteService noteService;

    @Inject
    private INiveauDao niveauDao;

    @Inject
    private IEnseignantDao enseignantDao;

    @Inject
    private IEtudiantDao etudiantDao;

    @Inject
    private IOptionDao optionDao;

    @Inject
    private ICoursDao coursDao;

    @Inject
    private IAnneeAcademiqueDao academiqueDao;

    @Inject
    private IDepartementDao departementDao;

    @Inject
    private ICreditDao creditDao;

    // TODO Royken I faut eviter les dependances vers les services
    @Inject
    private IEvaluationDao EvaluationDao;

    @Inject
    private IProgrammeDao programmeDao;

    @Inject
    private IParcoursDao parcoursDao;

    @Inject
    private ISemestreDao semestreDao;

    @Inject
    private IUniteEnseignementDao uniteEnsDao;

    @Inject
    private DocumentCommon common;

    public IEtudiantDao getEtudiantDao() {
        return etudiantDao;
    }

    public void setEtudiantDao(IEtudiantDao etudiantDao) {
        this.etudiantDao = etudiantDao;
    }

//    @Inject
//    private IUniteEnseignementDao uniteDao;
//    
//    @Inject
//    private ISemestreDao semestreDao;
    public INoteService getNoteService() {
        return noteService;
    }

    public void setNoteService(INoteService noteService) {
        this.noteService = noteService;
    }

    public INiveauDao getNiveauDao() {
        return niveauDao;
    }

    public void setNiveauDao(INiveauDao niveauDao) {
        this.niveauDao = niveauDao;
    }

    public IOptionDao getOptionDao() {
        return optionDao;
    }

    public void setOptionDao(IOptionDao optionDao) {
        this.optionDao = optionDao;
    }

    public ICoursDao getCoursDao() {
        return coursDao;
    }

    public void setCoursDao(ICoursDao coursDao) {
        this.coursDao = coursDao;
    }

    public IAnneeAcademiqueDao getAcademiqueDao() {
        return academiqueDao;
    }

    public void setAcademiqueDao(IAnneeAcademiqueDao academiqueDao) {
        this.academiqueDao = academiqueDao;
    }

    public IDepartementDao getDepartementDao() {
        return departementDao;
    }

    public void setDepartementDao(IDepartementDao departementDao) {
        this.departementDao = departementDao;
    }

    public IEnseignantDao getEnseignantDao() {
        return enseignantDao;
    }

    public void setEnseignantDao(IEnseignantDao enseignantDao) {
        this.enseignantDao = enseignantDao;
    }

    public IProgrammeDao getProgrammeDao() {
        return programmeDao;
    }

    public void setProgrammeDao(IProgrammeDao programmeDao) {
        this.programmeDao = programmeDao;
    }

    public IParcoursDao getParcoursDao() {
        return parcoursDao;
    }

    public void setParcoursDao(IParcoursDao parcoursDao) {
        this.parcoursDao = parcoursDao;
    }

    public ISemestreDao getSemestreDao() {
        return semestreDao;
    }

    public void setSemestreDao(ISemestreDao semestreDao) {
        this.semestreDao = semestreDao;
    }

    public IEvaluationDao getEvaluationDao() {
        return EvaluationDao;
    }

    public void setEvaluationDao(IEvaluationDao EvaluationDao) {
        this.EvaluationDao = EvaluationDao;
    }

    public IUniteEnseignementDao getUniteEnsDao() {
        return uniteEnsDao;
    }

    public void setUniteEnsDao(IUniteEnseignementDao uniteEnsDao) {
        this.uniteEnsDao = uniteEnsDao;
    }

    public ICreditDao getCreditDao() {
        return creditDao;
    }

    public void setCreditDao(ICreditDao creditDao) {
        this.creditDao = creditDao;
    }

    public DocumentCommon getCommon() {
        return common;
    }

    public void setCommon(DocumentCommon common) {
        this.common = common;
    }

    @Override
    public String produireSynthese(Long niveauId, Long optionId, Long academiqueId, Long semestreId, OutputStream stream) throws ServiceException {
        if (semestreId == null) {
            produireSyntheseAnnuelle(stream, niveauId, optionId, academiqueId);
        } else {
            produireSyntheseSemestrielle(niveauId, optionId, academiqueId, semestreId, stream);
        }
        return null;
    }

    private String produireSyntheseSemestrielle(Long niveauId, Long optionId, Long academiqueId, Long semestreId, OutputStream stream) {
        Document doc = null;
        try {
            doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4.rotate());
            doc.open();
            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            AnneeAcademique anne = academiqueDao.findById(academiqueId);
            Semestre semestre = semestreDao.findById(semestreId);
            common.produceDocumentHeader(doc, null, niveau, option, anne, null, null, null, true);
            produceSyntheseSemestrielleBody(doc, niveau, option, semestre, anne);
            doc.close();
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (doc != null) {
            doc.close();
        }
        return null;
    }

    private String produireSyntheseAnnuelle(OutputStream stream, Long niveauId, Long optionId, Long academiqueId) throws ServiceException {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4.rotate());
            doc.open();

            Niveau niveau = niveauDao.findById(niveauId);
            Option option = optionDao.findById(optionId);
            AnneeAcademique anne = academiqueDao.findById(academiqueId);
            common.produceDocumentHeader(doc, null, niveau, option, anne, null, null, null, true);
            produireSyntheseAnnueleBody(doc, niveau, option, anne);
            doc.close();
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void produceSyntheseSemestrielleBody(Document doc, Niveau n, Option o, Semestre s, AnneeAcademique a) {
        try {
            doc.add(new Phrase("\n"));
            // Je dois optimiser un peu les choses ici
            // Je recupere les différentes années
            // Pour chaque année, je produit un tableau
            Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
            Font title = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Paragraph parag = new Paragraph(String.format("SYNTHESE DU %s. %s, %s, %s.\n ANNÉE ACADÉMIQUE %s",
                    s.getIntitule().toUpperCase(),
                    o.getDepartement().getCode().toUpperCase(),
                    o.getDescription().toUpperCase(),
                    n.getCode().toUpperCase(),
                    a.toString()), title);
            parag.setAlignment(Element.ALIGN_CENTER);
            parag.setSpacingAfter(10f);
            doc.add(parag);
            boolean firstPage = true;
            List<AnneeAcademique> annees = academiqueDao.findAllYearWthNote(a, n, o, s);

            for (AnneeAcademique annee : annees) {
                //List<UEnseignementCredit> ues = uniteEnsDao.findByNiveauOptionSemestre(n, o, s, a);
                List<Etudiant> etudiants = etudiantDao.listeEtudiantAvecNotes(annee, a, n, o, s);
                if (etudiants.isEmpty()) {
                    continue;
                }
                List<UEnseignementCredit> ues = uniteEnsDao.findByNiveauOptionSemestre(n, o, s, annee);

                int taille = ues.size();
                int totalCredit = common.computeTotalCredit(ues);
                float relativeWidths[];
                relativeWidths = new float[6 + taille];
                relativeWidths[0] = 1;
                relativeWidths[1] = 10;
                relativeWidths[2] = 3;
                for (int i = 0; i < taille + 3; i++) {
                    relativeWidths[3 + i] = 2;
                }
                PdfPTable table = new PdfPTable(relativeWidths);
                if (!firstPage) {
                    doc.newPage();
                }
                firstPage = false;
                table.setWidthPercentage(100);
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("", bf, false));
                // Entete de synthese
                PdfPCell cell = new PdfPCell(new Phrase(s.getIntitule(), bf));
                cell.setColspan(taille + 5);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                table.getDefaultCell().setBorderColor(BaseColor.BLACK);
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("No", bf, false));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Noms et Prénoms", bf, false));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Matricule", bf, false));
                for (UEnseignementCredit ue : ues) {
                    table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell(ue.getIntituleUE(), bf, false));
                }
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Moyenne " + s.getIntitule(), bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Crédits " + s.getIntitule() + " validés", bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("% crédits " + s.getIntitule() + " validés", bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("", bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("", bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("", bf, true));
                for (UEnseignementCredit ue : ues) {
                    table.addCell(DocumentUtil.createDefaultBodyCell(String.valueOf(ue.getCredit()), bf, true));
                }
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell(String.valueOf(totalCredit), bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));

                int i = 1;
                List<UniteEnseignement> totos = uniteEnsDao.findByUniteNiveauOptionSemestre(n, o, s, annee);
                for (Etudiant etudiant : etudiants) {
                    int nombreCredit = 0;
                    double sumMoyenne = 0.0; //sumMoyenne /30 renvoie à la moyenne trimestrielle
                    int nbrCreditValide = 0; // le nombre de crédits validés  
                    // TODO I need to figure out how to speed this computation
                    Map<String, MoyenneUniteEnseignement> notes = noteService.listeNoteUniteEnseignement(etudiant.getMatricule(), a.getId(),annee.getId(), totos);
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.valueOf(i++), bf1, false, true));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(etudiant.getNom(), bf1, false, false));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(etudiant.getMatricule(), bf1, false, true));
                    for (UEnseignementCredit ue : ues) {
                        MoyenneUniteEnseignement mue = notes.get(ue.getCodeUE());
                        Double value = mue.getMoyenne();
                        sumMoyenne += value * ue.getCredit();
                        nombreCredit += ue.getCredit();
                        if (value >= 10) {
                            nbrCreditValide += ue.getCredit();
                        }
                        table.addCell(DocumentUtil.createSyntheseDefaultBodyCell((value == null) ? "" : String.format("%.2f", value), bf1, false, true));
                    }
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.format("%.2f", Math.ceil(sumMoyenne * 100 / nombreCredit) / 100), bf1, true, true));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.valueOf(nbrCreditValide), bf1, true, true));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.format("%.2f", (((nbrCreditValide * 1.0 / nombreCredit)) * 100)), bf1, true, true));
                }
                doc.add(table);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void produireSyntheseAnnueleBody(Document doc, Niveau n, Option o, AnneeAcademique a) {
        try {
            doc.add(new Phrase("\n"));
            boolean firstPage = true;
            List<AnneeAcademique> annees = academiqueDao.findAllYearWthNote(a, n, o, null);
            List<Semestre> semestres = semestreDao.findByNiveau(n);
            for (AnneeAcademique annee : annees) {
                if (!firstPage) {
                    doc.newPage();
                }
                List<Etudiant> etudiants = etudiantDao.listeEtudiantAvecNotes(annee, a, n, o, null);
                if (etudiants.isEmpty()) {
                    continue;
                }
                //Liste des ues du semestre 1
                List<UEnseignementCredit> ues1 = uniteEnsDao.findByNiveauOptionSemestre(n, o, semestres.get(0), annee);

                //Liste des ues du semestre 2
                List<UEnseignementCredit> ues2 = uniteEnsDao.findByNiveauOptionSemestre(n, o, semestres.get(1), annee);

                int creditSem1 = common.computeTotalCredit(ues1);

                int creditSem2 = common.computeTotalCredit(ues2);

            // La liste des étudiants du parcours
                //Parcours p = parcoursDao.findByNiveauOption(n, o);
                Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
                Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 6);
                Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 5);
                Font title = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Paragraph parag = new Paragraph(String.format("SYNTHESE ANNUELLE. %s, %s, %s.\n ANNÉE ACADÉMIQUE %s",
                        o.getDepartement().getCode().toUpperCase(),
                        o.getDescription().toUpperCase(),
                        n.getCode().toUpperCase(),
                        a.toString()), title);
                parag.setAlignment(Element.ALIGN_CENTER);
                parag.setSpacingAfter(10f);
                doc.add(parag);
            //List<Etudiant> etudiants = etudiantDao.listeEtudiantParDepartementEtParcours(o.getDepartement(), a, p);

                int taille1 = ues1.size();
                int taille2 = ues2.size();
                float relativeWidths[];
                relativeWidths = new float[11 + taille1 + taille2];
                relativeWidths[0] = 1;
                relativeWidths[1] = 10;
                relativeWidths[2] = 3;
                for (int i = 0; i < taille1 + taille2 + 8; i++) {
                    relativeWidths[3 + i] = 2;
                }
                PdfPTable table = new PdfPTable(relativeWidths);
                
                firstPage = false;
                table.setWidthPercentage(100);

                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("", bf, false));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("", bf, false));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("", bf, false));
                PdfPCell cell = new PdfPCell(new Phrase("Synthese I", bf));
                cell.setColspan(taille1 + 3);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                PdfPCell cell1 = new PdfPCell(new Phrase("Synthese II", bf));
                cell1.setColspan(taille2 + 3);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("", bf, false));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("", bf, false));

                table.getDefaultCell().setBorderColor(BaseColor.BLACK);
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("No", bf, false));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Noms et Prénoms", bf, false));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Matricule", bf, false));
                for (UEnseignementCredit ue : ues1) {
                    table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell(ue.getIntituleUE(), bf, false));
                }
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Moyenne I", bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Crédits " + semestres.get(0).getIntitule() + " validés", bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("% crédits " + semestres.get(0).getIntitule() + " validés", bf, true));
                for (UEnseignementCredit ue : ues2) {
                    table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell(ue.getIntituleUE(), bf, false));
                }

                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Moyenne II", bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Crédits " + semestres.get(1).getIntitule() + " validés", bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("% crédits " + semestres.get(1).getIntitule() + " validés", bf, true));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("Moyenne annuelle", bf, false));
                table.addCell(DocumentUtil.createSyntheseDefaultHeaderCell("% annuel de crédit validés", bf, false));
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                for (UEnseignementCredit ue : ues1) {
                    table.addCell(DocumentUtil.createDefaultBodyCell(String.valueOf(ue.getCredit()), bf, true));
                }
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell(String.valueOf(creditSem1), bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                for (UEnseignementCredit ue : ues2) {
                    table.addCell(DocumentUtil.createDefaultBodyCell(String.valueOf(ue.getCredit()), bf, true));
                }
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell(String.valueOf(creditSem2), bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                table.addCell(DocumentUtil.createDefaultBodyCell("", bf, true));
                //   table.addCell(createDefaultBodyCell("", bf, true));

                int i = 1;
                List<UniteEnseignement> uniteEns1 = uniteEnsDao.findByUniteNiveauOptionSemestre(n, o, semestres.get(0), annee);
                List<UniteEnseignement> uniteEns2 = uniteEnsDao.findByUniteNiveauOptionSemestre(n, o, semestres.get(1), annee);
                for (Etudiant etudiant : etudiants) {

                    double sumMoyenne1 = 0.0; // (sumMoyenne /nombreCredit1) renvoie à la moyenne trimestrielle
                    int nbrCreditValide1 = 0; // le nombre de  crédits validés
                    int nbrCreditValide2 = 0;
                    double sumMoyenne2 = 0;
                    Map<String, MoyenneUniteEnseignement> notes = noteService.listeNoteUniteEnseignement(etudiant.getMatricule(), a.getId(), annee.getId(), uniteEns1);
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.valueOf(i++), bf1, false, true));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(etudiant.getNom(), bf1, false, false));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(etudiant.getMatricule(), bf1, false, true));
                    for (UEnseignementCredit ue : ues1) {
                    // Double value = enue.getNote().get(ue.getCodeUE());
                        //Double value = notes.get(ue)
                        MoyenneUniteEnseignement mue = notes.get(ue.getCodeUE());
                        Double value = mue.getMoyenne();
                        sumMoyenne1 += value * ue.getCredit();
                        if (value >= 10) {
                            nbrCreditValide1 += ue.getCredit();
                        }
                        table.addCell(DocumentUtil.createSyntheseDefaultBodyCell((value == null) ? "" : String.format("%.2f", value), bf1, false, true));
                    }
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.format("%.2f", Math.ceil(sumMoyenne1 * 100 / creditSem1) / 100), bf1, true, true));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.valueOf(nbrCreditValide1), bf1, true, true));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.format("%.2f", (((nbrCreditValide1 * 1.0 / creditSem1)) * 100)), bf1, true, true));

                    // Le second semestre
                    Map<String, MoyenneUniteEnseignement> notes2 = noteService.listeNoteUniteEnseignement(etudiant.getMatricule(), a.getId(), annee.getId(), uniteEns2);
                    for (UEnseignementCredit ue : ues2) {
                        MoyenneUniteEnseignement mue = notes2.get(ue.getCodeUE());
                        double value = 0;
                        if (mue != null) {
                            value = mue.getMoyenne();
                            sumMoyenne2 += value * ue.getCredit();
                            if (value >= 10) {
                                nbrCreditValide2 += ue.getCredit();
                            }
                        }
                        table.addCell(DocumentUtil.createSyntheseDefaultBodyCell((value == 0) ? "" : String.format("%.2f", value), bf1, false, true));
                    }
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.format("%.2f", (sumMoyenne2 / creditSem2)), bf1, true, true));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.valueOf(nbrCreditValide2), bf1, true, true));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.format("%.2f", (((nbrCreditValide2 * 1.0 / creditSem2)) * 100)), bf1, true, true));

                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.format("%.2f", ((sumMoyenne1 + sumMoyenne2) / (creditSem1 + creditSem2))), bf1, true, true));
                    // table.addCell(createSyntheseDefaultBodyCell(String.valueOf(nbrCreditValide), bf1, true));
                    table.addCell(DocumentUtil.createSyntheseDefaultBodyCell(String.format("%.2f", ((((nbrCreditValide1 + nbrCreditValide2) * 1.0 / (creditSem1 + creditSem2))) * 100)), bf1, true, true));
                }
                doc.add(table);
            }

        } catch (DocumentException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(DocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
