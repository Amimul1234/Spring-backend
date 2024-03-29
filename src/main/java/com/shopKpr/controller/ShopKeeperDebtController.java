package com.shopKpr.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import com.shopKpr.entity.shopKeeper_related.UserDebts;
import com.shopKpr.entity.shopKeeper_related.User_debt_details;
import com.shopKpr.service.shop_keeper_related.Debt.ShopUserDebtService;
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
public class ShopKeeperDebtController {

    private final ShopUserDebtService shopUserDebtService;

    public ShopKeeperDebtController(ShopUserDebtService shopUserDebtService) {
        this.shopUserDebtService = shopUserDebtService;
    }

    @PostMapping("/addUserDebt") //this is the first time when shop keeper will add user to debt list
    public ResponseEntity addUserDebt(@RequestBody UserDebts userDebts, @RequestParam(name = "shop_mobile_number") String shop_mobile_number)
    {
        return shopUserDebtService.addDebt(userDebts, shop_mobile_number);
    }

    @GetMapping("/getUserDebtLists")
    public ResponseEntity getUserDebtLists(@RequestParam(name = "page") int page, @RequestParam(name = "shop_mobile_number") String shop_mobile_number)
    {
        return shopUserDebtService.getAllDebts(page, shop_mobile_number);
    }

    @PostMapping("/addAdebtDetails") //This is for adding debt details of an existing user
    public ResponseEntity addAdebtDetails(@RequestBody User_debt_details user_debt_details, @RequestParam(name = "user_id") Long user_id)
    {
        return shopUserDebtService.addDebtDetails(user_debt_details, user_id);
    }

    @DeleteMapping("/deleteAdebtDetails")
    public ResponseEntity deleteAdebtDetails(@RequestParam(name = "id_of_debt_details") long id_of_debt_details, @RequestParam(name = "user_id") long user_id)
    {
        return shopUserDebtService.deleteAdebtDetails(id_of_debt_details, user_id);
    }

    @PutMapping("/updateAdebtDetails") //Updating a customer's debt_details
    public ResponseEntity updateAdebtDetails(@RequestBody User_debt_details user_debt_details, @RequestParam(name = "user_id") long user_id)
    {
        return shopUserDebtService.updateAdebtDetails(user_debt_details, user_id);
    }

    @DeleteMapping("/clearAllDebtDetails") //this is for clearing a customer all debt details
    public ResponseEntity clearAllDebtDetails(@RequestParam(name = "user_id") Long user_id)
    {
        return shopUserDebtService.clearAllDebtDetails(user_id);
    }


    @GetMapping("/getUserSpecificDebtDetails") //This method is for getting debt details for an user
    public ResponseEntity getAllDebtDetails(@RequestParam(name = "user_id") Long user_id)
    {
        return shopUserDebtService.getAllDebtDetails(user_id);
    }

    @GetMapping("/getADebtListForAUser")
    public ResponseEntity getADebtListForUser(@RequestParam(name = "user_id") Long user_id)
    {
        return shopUserDebtService.getDebtDetailsForACustomer(user_id);
    }

    @GetMapping("/getAllDebtDetailsReport") //This method is for getting pdf report of the debt for a user
    public ResponseEntity<Resource> generateExcelReport(@RequestParam(name = "user_id") Long user_id) throws DocumentException
    {

        List<User_debt_details> user_debt_details = shopUserDebtService.getAllDebtDetailsViaList(user_id);

        String customer_name = shopUserDebtService.getCustomerName(user_id);

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

        return new ResponseEntity<>(new InputStreamResource(is), headers,
                HttpStatus.OK);
    }
}
