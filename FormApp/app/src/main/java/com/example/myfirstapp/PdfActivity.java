package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.net.Uri;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabStop;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class PdfActivity extends AppCompatActivity {

    private static  final int STORAGE_CODE = 1000;
    //declaring views
    EditText mTextEt;
    Button mSaveBtn;
    TextView mTitle;
    TextView mCheckItem1;
    TextView mCheckItem2;
    TextView mCheckItem3;
    TextView mCheckItem4;
    TextView mCheckItem5;
    TextView mCheckItem6;
    TextView mCheckItem7;
    CheckBox mCheckbox1;
    CheckBox mCheckbox2;
    CheckBox mCheckbox3;
    CheckBox mCheckbox4;
    CheckBox mCheckbox5;
    CheckBox mCheckbox6;
    CheckBox mCheckbox7;
    String pdfToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        //initialize views (activity_main.xml)
        mTextEt = findViewById(R.id.textEt);
        mSaveBtn = findViewById(R.id.saveBtn);
        mTitle = findViewById(R.id.textTitle);
        mCheckItem1 = findViewById((R.id.checkItem1));
        mCheckItem2 = findViewById((R.id.checkItem2));
        mCheckItem3 = findViewById((R.id.checkItem3));
        mCheckItem4 = findViewById((R.id.checkItem4));
        mCheckItem5 = findViewById((R.id.checkItem5));
        mCheckItem6 = findViewById((R.id.checkItem6));
        mCheckItem7 = findViewById((R.id.checkItem7));
        mCheckbox1 = findViewById(R.id.checkBox1);
        mCheckbox2 = findViewById(R.id.checkBox2);
        mCheckbox3 = findViewById(R.id.checkBox3);
        mCheckbox4 = findViewById(R.id.checkBox4);
        mCheckbox5 = findViewById(R.id.checkBox5);
        mCheckbox6 = findViewById(R.id.checkBox6);
        mCheckbox7 = findViewById(R.id.checkBox7);

        //handle button click
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check runtime permission for device running marshmallow or above
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else
                    {
                        //permission already granted
                        savePdf();
                    }
                }
                else
                {
                    //system OS < Marshmallow
                    savePdf();
                }
            }
        });
    }

    //This is where we send our email with the attachment
    private void sendEmail(String mFilePath){
        try {
            //Create a URI for our PDF
            File pdfToSend = new File(mFilePath);
            Uri URI = FileProvider.getUriForFile(this, "com.USACEARPA.provider", pdfToSend);

            String mFileName = new SimpleDateFormat("yyyyMMdd",
                    Locale.getDefault()).format((System.currentTimeMillis()));

            //Creating an intent, so we can pass our email + attachment to an email client of choice
            Intent it = new Intent(Intent.ACTION_SEND);
            it.setType("application/pdf");
            it.putExtra(Intent.EXTRA_EMAIL, new String[]{"exampleEmail@example.com"});
            it.putExtra(Intent.EXTRA_SUBJECT, mFileName + " Report");
            it.putExtra(Intent.EXTRA_TEXT, "Sent from ARPA mobile device.");
            it.putExtra(Intent.EXTRA_STREAM, URI);

            pdfToDelete = mFilePath;

            startActivity(it);
            startActivityForResult(it, 1);

        }
        catch(Throwable t)
        {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        File deletingFile = new File(pdfToDelete);

        //Cleanup, delete file once we're done with it.
        if(deletingFile.delete()) {
            assert true;
        }else{
            Toast.makeText(this, "Failed to delete file successfully", Toast.LENGTH_LONG).show();
        }
    }

    private void savePdf()
    {
        Document mDoc = new Document();
        //pdf filename
        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format((System.currentTimeMillis()));

        //pdf file path
        String mFilePath =  Environment.getExternalStorageDirectory() +  "/" + mFileName + ".pdf";
        Toast.makeText(this, mFilePath, Toast.LENGTH_SHORT).show();

        try
        {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            //open doc for writing
            mDoc.open();

            String mTextTitle = mTitle.getText().toString();

            String mTextCheck1 = mCheckItem1.getText().toString();
            String mTextCheck2 = mCheckItem2.getText().toString();
            String mTextCheck3 = mCheckItem3.getText().toString();
            String mTextCheck4 = mCheckItem4.getText().toString();
            String mTextCheck5 = mCheckItem5.getText().toString();
            String mTextCheck6 = mCheckItem6.getText().toString();
            String mTextCheck7 = mCheckItem7.getText().toString();

            Boolean mCheck1 = mCheckbox1.isChecked();
            Boolean mCheck2 = mCheckbox2.isChecked();
            Boolean mCheck3 = mCheckbox3.isChecked();
            Boolean mCheck4 = mCheckbox4.isChecked();
            Boolean mCheck5 = mCheckbox5.isChecked();
            Boolean mCheck6 = mCheckbox6.isChecked();
            Boolean mCheck7 = mCheckbox7.isChecked();

            //get text from EditText
            String mText = mTextEt.getText().toString();

            //add author of document (optional)
            //mDoc.addAuthor("");

            Font titleFont = new Font(BaseFont.createFont(), 40f, Font.BOLD);
            Font boldFont = new Font(BaseFont.createFont(), 20f, Font.BOLD);
            Font baseFont = new Font(BaseFont.createFont(), 20f);
            Font dingbats = new Font((Font.FontFamily.ZAPFDINGBATS), 20f);

            Paragraph p1 = new Paragraph(mTextCheck1 + ":", baseFont);
            Paragraph p2 = new Paragraph(mTextCheck2 + ":", baseFont);
            Paragraph p3 = new Paragraph(mTextCheck3 + ":", baseFont);
            Paragraph p4 = new Paragraph(mTextCheck4 + ":", baseFont);
            Paragraph p5 = new Paragraph(mTextCheck5 + ":", baseFont);
            Paragraph p6 = new Paragraph(mTextCheck6 + ":", baseFont);
            Paragraph p7 = new Paragraph(mTextCheck7 + ":", baseFont);

            Chunk checkChunk = new Chunk("3", dingbats);
            Chunk exChunk = new Chunk("5", dingbats);
            Paragraph checkPhrase = new Paragraph(checkChunk);
            Paragraph exPhrase = new Paragraph(exChunk);

            //create table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100f);

            //add title to PDF
            mDoc.add(new Paragraph(mTextTitle, titleFont));
            mDoc.add(new Paragraph(" "));

            //create cells and add them to table
            PdfPCell cell1 = new PdfPCell(p1);
            cell1.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell();
            if (mCheck1 == true)
            {
                cell2.addElement(checkPhrase);
            }
            else
                cell2.addElement(exPhrase);
            cell2.setBorder(PdfPCell.NO_BORDER);
            cell2.setUseAscender(true);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(p2);
            cell3.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell();
            if (mCheck2 == true)
            {
                cell4.addElement(checkPhrase);
            }
            else
                cell4.addElement(exPhrase);
            cell4.setBorder(PdfPCell.NO_BORDER);
            cell4.setUseAscender(true);
            cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell4);

            PdfPCell cell5 = new PdfPCell(p3);
            cell5.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell5);

            PdfPCell cell6 = new PdfPCell();
            if (mCheck3 == true)
            {
                cell6.addElement(checkPhrase);
            }
            else
                cell6.addElement(exPhrase);
            cell6.setBorder(PdfPCell.NO_BORDER);
            cell6.setUseAscender(true);
            cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell6);

            PdfPCell cell7 = new PdfPCell(p4);
            cell7.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell7);

            PdfPCell cell8 = new PdfPCell();
            if (mCheck4 == true)
            {
                cell8.addElement(checkPhrase);
            }
            else
                cell8.addElement(exPhrase);
            cell8.setBorder(PdfPCell.NO_BORDER);
            cell8.setUseAscender(true);
            cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell8);

            PdfPCell cell9 = new PdfPCell(p5);
            cell9.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell9);

            PdfPCell cell10 = new PdfPCell();
            if (mCheck5 == true)
            {
                cell10.addElement(checkPhrase);
            }
            else
                cell10.addElement(exPhrase);
            cell10.setBorder(PdfPCell.NO_BORDER);
            cell10.setUseAscender(true);
            cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell10);

            PdfPCell cell11 = new PdfPCell(p6);
            cell11.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell11);

            PdfPCell cell12 = new PdfPCell();
            if (mCheck6 == true)
            {
                cell12.addElement(checkPhrase);
            }
            else
                cell12.addElement(exPhrase);
            cell12.setBorder(PdfPCell.NO_BORDER);
            cell12.setUseAscender(true);
            cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell12);

            PdfPCell cell13 = new PdfPCell(p7);
            cell13.setBorder(PdfPCell.NO_BORDER);
            table.addCell(cell13);

            PdfPCell cell14 = new PdfPCell();
            if (mCheck7 == true)
            {
                cell14.addElement(checkPhrase);
            }
            else
                cell14.addElement(exPhrase);
            cell14.setBorder(PdfPCell.NO_BORDER);
            cell14.setUseAscender(true);
            cell14.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell14);

            //add table to the PDF
            mDoc.add(table);

            //add additional paragraph for notes to PDF
            mDoc.add(new Paragraph(" "));
            mDoc.add(new Paragraph("Additional Comments:", boldFont));
            mDoc.add(new Paragraph(mText, baseFont));
            mDoc.close();
           // Toast.makeText(this, mFileName + ".pdf\nis saved to\n" + mFilePath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        sendEmail(mFilePath);

    }

    //handle permission result

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case STORAGE_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission was granted from popup, call savePdf
                    savePdf();
                }
                else
                {
                    //permission was denied from popup, show error message
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



}
