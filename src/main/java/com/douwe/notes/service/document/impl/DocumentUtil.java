package com.douwe.notes.service.document.impl;

import com.douwe.notes.entities.Session;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class DocumentUtil {
    
    public static String transformNoteGrade(double note) {
        if (note <= 20 && note >= 16) {
            return "A+";
        }
        if (note < 16 && note >= 15) {
            return "A";
        }
        if (note < 15 && note >= 14) {
            return "B+";
        }
        if (note < 14 && note >= 13) {
            return "B";
        }
        if (note < 13 && note >= 12) {
            return "B-";
        }
        if (note < 12 && note >= 11) {
            return "C+";
        }
        if (note < 11 && note >= 10) {
            return "C";
        }
        if (note < 10 && note >= 9) {
            return "C-";
        }
        if (note < 9 && note >= 8) {
            return "D+";
        }
        if (note < 8 && note >= 7) {
            return "D";
        }
        if (note < 7 && note >= 6) {
            return "E";
        }

        return "F";

    }
    
    public static double transformNoteMgp(double note) {
        double notep = note * 5;
        if (notep <= 100 && notep >= 80) {
            return 4;
        }
        if (notep < 80 && notep >= 75) {
            return 3.7;
        }
        if (notep < 75 && notep >= 70) {
            return 3.3;
        }
        if (notep < 70 && notep >= 65) {
            return 3.0;
        }
        if (notep < 65 && notep >= 60) {
            return 2.7;
        }
        if (notep < 60 && notep >= 55) {
            return 2.3;
        }
        if (notep < 55 && notep >= 50) {
            return 2.0;
        }
        if (notep < 50 && notep >= 45) {
            return 1.7;
        }
        if (notep < 45 && notep >= 40) {
            return 1.3;
        }
        if (note < 40 && note >= 35) {
            return 1.0;
        }
        

        return 0;

    }
    
    public static String transformNoteMention(double note) {
        if (note <= 20 && note >= 16) {
            return "Très bien";
        }
        if (note < 16 && note >= 14) {
            return "Bien";
        }
        if (note < 14 && note >= 12) {
            return "Assez bien";
        }
        if (note < 12 && note >= 10) {
            return "Passable";
        }
        if (note < 10 && note >= 7) {
            return "Insuffisant";
        }

        return "Echec";

    }
    
    public static String sessionToString(Session session) {
        if (session == Session.normale) {
            return "1";
        } else {
            return "2";
        }
    }
    
    public static PdfPCell createDefaultHeaderCell(String message, Font bf) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        cell.setBackgroundColor(new BaseColor(230, 230, 230));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public static PdfPCell createSyntheseDefaultHeaderCell(String message, Font bf, boolean color) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        if (color) {
            cell.setBackgroundColor(new BaseColor(230, 230, 230));
        }
        // cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);

        cell.setBorderColor(BaseColor.BLACK);

        cell.setRotation(90);
        return cell;
    }

    public static PdfPCell createDefaultBodyCell(String message, Font bf, boolean color) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        if (color) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        }
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }

    public static PdfPCell createRelevetFootBodyCell(String message, Font bf, boolean border, int rowspan, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        if (border) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        }
        cell.setRowspan(rowspan);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.WHITE);
        return cell;
    }

    public static PdfPCell createSyntheseDefaultBodyCell(String message, Font bf, boolean color, boolean isCentered) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        if (color) {
            cell.setBackgroundColor(new BaseColor(230, 230, 230));
        }
        if (isCentered) {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        } else {
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        }
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(4f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }
}
