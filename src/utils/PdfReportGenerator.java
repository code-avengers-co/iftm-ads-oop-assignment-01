package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Order;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfReportGenerator {
    private static String DIRECTORY_BASE = "pdfs";

    public static void generateOrdersReport(List<Order> orders, String destPath) {
        String finalPath = DIRECTORY_BASE + "/" + destPath;
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(finalPath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Relatório de Pedidos", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            
            PdfPCell c1 = new PdfPCell(new Phrase("ID", headerFont));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            PdfPCell c2 = new PdfPCell(new Phrase("Cliente", headerFont));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c2);

            PdfPCell c3 = new PdfPCell(new Phrase("Status", headerFont));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c3);

            PdfPCell c4 = new PdfPCell(new Phrase("Valor Total", headerFont));
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c4);

            PdfPCell c5 = new PdfPCell(new Phrase("Data Criação", headerFont));
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c5);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

            for (Order order : orders) {
                PdfPCell idCell = new PdfPCell(new Phrase(String.valueOf(order.getId()), cellFont));
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                table.addCell(new Phrase(order.getUser().getPerson().getName(), cellFont));
                
                table.addCell(new Phrase(order.getStatus().name(), cellFont));
                
                PdfPCell valueCell = new PdfPCell(new Phrase(String.format("R$ %.2f", order.getTotalValue()), cellFont));
                valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(valueCell);
                
                PdfPCell dateCell = new PdfPCell(new Phrase(order.getCreatedAt().format(formatter), cellFont));
                dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(dateCell);
            }

            document.add(table);

            System.out.println("Relatório PDF de pedidos gerado com sucesso em: " + finalPath);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}
