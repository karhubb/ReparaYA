package com.reparaya.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.reparaya.model.Servicio;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    // --- EXCEL ---
    public void exportarExcel(HttpServletResponse response, List<Servicio> lista) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Servicios");

        // Estilo para la cabecera
        CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        // Cabecera
        Row headerRow = sheet.createRow(0);
        String[] columnas = {"ID", "Fecha", "Cliente", "Equipo", "Falla", "Estado", "Costo", "Técnico"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        int rowNum = 1;
        for (Servicio s : lista) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(s.getId());
            row.createCell(1).setCellValue(s.getFechaIngreso().toString());
            row.createCell(2).setCellValue(s.getCliente() != null ? s.getCliente().getNombre() : "---");
            row.createCell(3).setCellValue(s.getMarca() + " " + s.getModelo());
            row.createCell(4).setCellValue(s.getFalla());
            row.createCell(5).setCellValue(s.getEstado());
            row.createCell(6).setCellValue(s.getCostoTotal());
            row.createCell(7).setCellValue(s.getTecnicoAsignado() != null ? s.getTecnicoAsignado().getNombre() : "Sin asignar");
        }

        // Ajustar columnas
        for (int i = 0; i < columnas.length; i++) sheet.autoSizeColumn(i);

        // Escribir
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    // --- PDF ---
    public void exportarPDF(HttpServletResponse response, List<Servicio> lista) throws IOException {
        Document document = new Document(PageSize.A4.rotate()); // Horizontal
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Título
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph title = new Paragraph("Reporte de Servicios", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); // Espacio

        // Tabla
        PdfPTable table = new PdfPTable(6); // 6 Columnas
        table.setWidthPercentage(100f);

        // Cabeceras
        String[] headers = {"Fecha", "Cliente", "Equipo", "Estado", "Costo", "Técnico"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Datos
        for (Servicio s : lista) {
            table.addCell(s.getFechaIngreso().toString());
            table.addCell(s.getCliente() != null ? s.getCliente().getNombre() : "---");
            table.addCell(s.getMarca() + " " + s.getModelo());
            table.addCell(s.getEstado());
            table.addCell("$" + s.getCostoTotal());
            table.addCell(s.getTecnicoAsignado() != null ? s.getTecnicoAsignado().getNombre() : "---");
        }

        document.add(table);
        document.close();
    }
}