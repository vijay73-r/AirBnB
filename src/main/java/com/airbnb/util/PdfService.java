package com.airbnb.util;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Booking;
import com.airbnb.service.EmailService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfService {

    private EmailService emailService;

    public PdfService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void generatePdf(Booking booking, String email) {
        try {
            // Generate the PDF and save it
            String fileName = "booking_" + booking.getId() + ".pdf";
            Path filePath = Paths.get("D://examples_api//bookings//" + fileName);
            Files.createDirectories(filePath.getParent());

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath.toFile()));

            document.open();

            PdfPTable table = new PdfPTable(3);
            addTableHeader(table);
            addRows(table, booking);

            document.add(table);
            document.close();

            // Send the email with the generated PDF as an attachment
            emailService.sendEmailWithAttachment(
                    email,  // Use the email passed to this method
                    "Booking Confirmation " + booking.getId(),
                    "Please find attached your booking confirmation details.",
                    filePath.toFile()
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while generating PDF or sending email", e);
        }
    }


    private void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Total Price", "City")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, Booking booking) {
        table.addCell(booking.getGuestName());
        table.addCell(String.valueOf(booking.getTotalPrice()));
        table.addCell(booking.getProperty().getCity().getName());
    }




}
