package com.owo.OwoDokan.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import com.owo.OwoDokan.entity.shopKeeper_related.UserDebts;
import com.owo.OwoDokan.entity.shopKeeper_related.User_debt_details;
import com.owo.OwoDokan.service.shop_keeper_related.Debt.ShopUserDebt;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
public class ShopUserController {

    private final ShopUserDebt shopUserDebt;

    public ShopUserController(ShopUserDebt shopUserDebt) {
        this.shopUserDebt = shopUserDebt;
    }

    @PostMapping("/addUserDebt") //this is the first time when shop keeper will add user to debt list
    public UserDebts addUserDebt(@RequestBody UserDebts userDebts)
    {
        return shopUserDebt.addDebt(userDebts);
    }

    @PostMapping("/addAdebtDetails") //This is for adding debt details of an existing user
    public void addAdebtDetails(@RequestBody User_debt_details user_debt_details, @RequestParam(name = "mobile_number") String mobile_number)
    {
        shopUserDebt.addDebtDetails(user_debt_details, mobile_number);
    }

    @DeleteMapping("/deleteAdebtDetails")
    public void deleteAdebtDetails(@RequestParam(name = "id_of_debt_details") long id_of_debt_details, @RequestParam(name = "mobile_number") String mobile_number)
    {
        shopUserDebt.deleteAdebtDetails(id_of_debt_details, mobile_number);
    }

    @PostMapping("/updateAdebtDetails") //Updating a customer's debt_details
    public void updateAdebtDetails(@RequestBody User_debt_details user_debt_details, @RequestParam(name = "mobile_number") String mobile_number)
    {
        shopUserDebt.updateAdebtDetails(user_debt_details, mobile_number);
    }

    @PutMapping("/clearAllDebtDetails") //this is for clearing a customer all debt details
    public void clearAllDebtDetails(@RequestParam(name = "mobile_number") String mobile_number)
    {
        shopUserDebt.clearAllDebtDetails(mobile_number);
    }


    @GetMapping("/getAllDebtDetails") //This method is for getting debt details for an user
    public List<User_debt_details> getAllDebtDetails(@RequestParam(name = "mobile_number") String mobile_number)
    {
        return shopUserDebt.getAllDebtDetails(mobile_number);
    }

    @GetMapping("/getAllDebtDetailsReport") //This method is for getting pdf report of the debt for a user
    public ResponseEntity<Resource> generateExcelReport(@RequestParam(name = "mobile_number") String mobile_number) throws IOException, DocumentException {

        List<User_debt_details> user_debt_details = shopUserDebt.getAllDebtDetails(mobile_number);

        String customer_name = shopUserDebt.getCustomerName(mobile_number);

        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, os);

        document.open();

        Paragraph title = new Paragraph(customer_name+" Debt details",
                FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, new BaseColor(255, 0, 0)));

        document.add(title);

        PdfPTable table = new PdfPTable(4);
        table.setSpacingBefore(25);
        table.setSpacingAfter(25);

        PdfPCell c1 = new PdfPCell(new Phrase("Number"));
        table.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Phrase("Description"));
        table.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase("Date"));
        table.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Due"));
        table.addCell(c4);

        int i;

        int length = user_debt_details.size();

        for(i=0; i<length; i++)
        {
            table.addCell(String.valueOf(i+1));
            table.addCell(user_debt_details.get(i).getDescription());
            table.addCell(user_debt_details.get(i).getDate());
            table.addCell(String.valueOf(user_debt_details.get(i).getTaka()));
        }

        document.add(table);

        document.close();

        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DebtDetails.pdf");

        ResponseEntity<Resource> response = new ResponseEntity<Resource>(new InputStreamResource(is), headers,
                HttpStatus.OK);

        return response;
    }


}
