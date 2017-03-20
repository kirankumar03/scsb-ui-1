package org.recap.util;

import com.csvreader.CsvWriter;
import org.apache.commons.collections.CollectionUtils;
import org.recap.RecapConstants;
import org.recap.model.reports.ReportsResponse;
import org.recap.model.search.DeaccessionItemResultsRow;
import org.recap.model.search.IncompleteReportResultsRow;
import org.recap.model.search.ReportsForm;
import org.recap.repository.jpa.ItemChangeLogDetailsRepository;
import org.recap.repository.jpa.RequestItemDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by akulak on 21/12/16.
 */
@Component
public class ReportsUtil {

    @Autowired
    ReportsServiceUtil reportsServiceUtil;

    @Autowired
    RequestItemDetailsRepository requestItemDetailsRepository;

    @Autowired
    ItemChangeLogDetailsRepository itemChangeLogDetailsRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReportsUtil.class);


    public void populateILBDCountsForRequest(ReportsForm reportsForm, Date requestFromDate, Date requestToDate) {
        reportsForm.setIlRequestPulCount(requestItemDetailsRepository.getIlRequestCounts(requestFromDate, requestToDate, RecapConstants.PUL_INST_ID, Arrays.asList(RecapConstants.CUL_INST_ID, RecapConstants.NYPL_INST_ID)));
        reportsForm.setIlRequestCulCount(requestItemDetailsRepository.getIlRequestCounts(requestFromDate, requestToDate, RecapConstants.CUL_INST_ID, Arrays.asList(RecapConstants.PUL_INST_ID, RecapConstants.NYPL_INST_ID)));
        reportsForm.setIlRequestNyplCount(requestItemDetailsRepository.getIlRequestCounts(requestFromDate, requestToDate, RecapConstants.NYPL_INST_ID, Arrays.asList(RecapConstants.PUL_INST_ID, RecapConstants.CUL_INST_ID)));
        reportsForm.setBdRequestPulCount(requestItemDetailsRepository.getBDHoldRecallRetrievalRequestCounts(requestFromDate, requestToDate, RecapConstants.PUL_INST_ID, RecapConstants.BORROW_DIRECT));
        reportsForm.setBdRequestCulCount(requestItemDetailsRepository.getBDHoldRecallRetrievalRequestCounts(requestFromDate, requestToDate, RecapConstants.CUL_INST_ID, RecapConstants.BORROW_DIRECT));
        reportsForm.setBdRequestNyplCount(requestItemDetailsRepository.getBDHoldRecallRetrievalRequestCounts(requestFromDate, requestToDate, RecapConstants.NYPL_INST_ID, RecapConstants.BORROW_DIRECT));
        reportsForm.setShowILBDResults(true);
        reportsForm.setShowReportResultsText(true);
        reportsForm.setShowNoteILBD(true);
    }

    public void populatePartnersCountForRequest(ReportsForm reportsForm, Date requestFromDate, Date requestToDate) {
        reportsForm.setPhysicalPrivatePulCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.PUL_INST_ID, Arrays.asList(RecapConstants.CGD_PRIVATE), Arrays.asList(RecapConstants.RETRIEVAL, RecapConstants.RECALL, RecapConstants.BORROW_DIRECT)));
        reportsForm.setPhysicalPrivateCulCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.CUL_INST_ID, Arrays.asList(RecapConstants.CGD_PRIVATE), Arrays.asList(RecapConstants.RETRIEVAL, RecapConstants.RECALL, RecapConstants.BORROW_DIRECT)));
        reportsForm.setPhysicalPrivateNyplCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.NYPL_INST_ID, Arrays.asList(RecapConstants.CGD_PRIVATE), Arrays.asList(RecapConstants.RETRIEVAL, RecapConstants.RECALL, RecapConstants.BORROW_DIRECT)));
        reportsForm.setPhysicalSharedPulCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.PUL_INST_ID, Arrays.asList(RecapConstants.CGD_SHARED, RecapConstants.CGD_OPEN), Arrays.asList(RecapConstants.RETRIEVAL, RecapConstants.RECALL, RecapConstants.BORROW_DIRECT)));
        reportsForm.setPhysicalSharedCulCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.CUL_INST_ID, Arrays.asList(RecapConstants.CGD_SHARED, RecapConstants.CGD_OPEN), Arrays.asList(RecapConstants.RETRIEVAL, RecapConstants.RECALL, RecapConstants.BORROW_DIRECT)));
        reportsForm.setPhysicalSharedNyplCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.NYPL_INST_ID, Arrays.asList(RecapConstants.CGD_SHARED, RecapConstants.CGD_OPEN), Arrays.asList(RecapConstants.RETRIEVAL, RecapConstants.RECALL, RecapConstants.BORROW_DIRECT)));
        reportsForm.setEddPrivatePulCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.PUL_INST_ID, Arrays.asList(RecapConstants.CGD_PRIVATE), Arrays.asList(RecapConstants.EDD)));
        reportsForm.setEddPrivateCulCount( requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.CUL_INST_ID, Arrays.asList(RecapConstants.CGD_PRIVATE), Arrays.asList(RecapConstants.EDD)));
        reportsForm.setEddPrivateNyplCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.NYPL_INST_ID, Arrays.asList(RecapConstants.CGD_PRIVATE), Arrays.asList(RecapConstants.EDD)));
        reportsForm.setEddSharedOpenPulCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.PUL_INST_ID, Arrays.asList(RecapConstants.CGD_SHARED, RecapConstants.CGD_OPEN), Arrays.asList(RecapConstants.EDD)));
        reportsForm.setEddSharedOpenCulCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.CUL_INST_ID, Arrays.asList(RecapConstants.CGD_SHARED, RecapConstants.CGD_OPEN), Arrays.asList(RecapConstants.EDD)));
        reportsForm.setEddSharedOpenNyplCount(requestItemDetailsRepository.getPhysicalAndEDDCounts(requestFromDate, requestToDate, RecapConstants.NYPL_INST_ID, Arrays.asList(RecapConstants.CGD_SHARED, RecapConstants.CGD_OPEN), Arrays.asList(RecapConstants.EDD)));
        reportsForm.setShowPartners(true);
        reportsForm.setShowReportResultsText(true);
        reportsForm.setShowNotePartners(true);
    }


    public void populateRequestTypeInformation(ReportsForm reportsForm, Date requestFromDate, Date requestToDate) {
        reportsForm.setRetrievalRequestPulCount(requestItemDetailsRepository.getBDHoldRecallRetrievalRequestCounts(requestFromDate, requestToDate, RecapConstants.PUL_INST_ID, RecapConstants.RETRIEVAL));
        reportsForm.setRetrievalRequestCulCount(requestItemDetailsRepository.getBDHoldRecallRetrievalRequestCounts(requestFromDate, requestToDate, RecapConstants.CUL_INST_ID, RecapConstants.RETRIEVAL));
        reportsForm.setRetrievalRequestNyplCount(requestItemDetailsRepository.getBDHoldRecallRetrievalRequestCounts(requestFromDate, requestToDate, RecapConstants.NYPL_INST_ID, RecapConstants.RETRIEVAL));
        reportsForm.setRecallRequestPulCount(requestItemDetailsRepository.getBDHoldRecallRetrievalRequestCounts(requestFromDate, requestToDate, RecapConstants.PUL_INST_ID, RecapConstants.RECALL));
        reportsForm.setRecallRequestCulCount(requestItemDetailsRepository.getBDHoldRecallRetrievalRequestCounts(requestFromDate, requestToDate, RecapConstants.CUL_INST_ID, RecapConstants.RECALL));
        reportsForm.setRecallRequestNyplCount(requestItemDetailsRepository.getBDHoldRecallRetrievalRequestCounts(requestFromDate, requestToDate, RecapConstants.NYPL_INST_ID, RecapConstants.RECALL));
        reportsForm.setShowRecallTable(true);
        reportsForm.setShowRetrievalTable(true);
        reportsForm.setShowReportResultsText(true);
        reportsForm.setShowRequestTypeTable(true);
        reportsForm.setShowNoteRequestType(true);
    }
    public void populateAccessionDeaccessionItemCounts(ReportsForm reportsForm) throws Exception {
        ReportsResponse reportsResponse = reportsServiceUtil.requestAccessionDeaccessionCounts(reportsForm);
        reportsForm.setAccessionPrivatePulCount(reportsResponse.getAccessionPrivatePulCount());
        reportsForm.setAccessionPrivateCulCount(reportsResponse.getAccessionPrivateCulCount());
        reportsForm.setAccessionPrivateNyplCount(reportsResponse.getAccessionPrivateNyplCount());

        reportsForm.setAccessionOpenPulCount(reportsResponse.getAccessionOpenPulCount());
        reportsForm.setAccessionOpenCulCount(reportsResponse.getAccessionOpenCulCount());
        reportsForm.setAccessionOpenNyplCount(reportsResponse.getAccessionOpenNyplCount());

        reportsForm.setAccessionSharedPulCount(reportsResponse.getAccessionSharedPulCount());
        reportsForm.setAccessionSharedCulCount(reportsResponse.getAccessionSharedCulCount());
        reportsForm.setAccessionSharedNyplCount(reportsResponse.getAccessionSharedNyplCount());

        reportsForm.setDeaccessionPrivatePulCount(reportsResponse.getDeaccessionPrivatePulCount());
        reportsForm.setDeaccessionPrivateCulCount(reportsResponse.getDeaccessionPrivateCulCount());
        reportsForm.setDeaccessionPrivateNyplCount(reportsResponse.getDeaccessionPrivateNyplCount());

        reportsForm.setDeaccessionOpenPulCount(reportsResponse.getDeaccessionOpenPulCount());
        reportsForm.setDeaccessionOpenCulCount(reportsResponse.getDeaccessionOpenCulCount());
        reportsForm.setDeaccessionOpenNyplCount(reportsResponse.getDeaccessionOpenNyplCount());

        reportsForm.setDeaccessionSharedPulCount(reportsResponse.getDeaccessionSharedPulCount());
        reportsForm.setDeaccessionSharedCulCount(reportsResponse.getDeaccessionSharedCulCount());
        reportsForm.setDeaccessionSharedNyplCount(reportsResponse.getDeaccessionSharedNyplCount());

        reportsForm.setShowAccessionDeaccessionTable(true);
    }


    public void populateCGDItemCounts(ReportsForm reportsForm) throws Exception {
        ReportsResponse reportsResponse = reportsServiceUtil.requestCgdItemCounts(reportsForm);
        reportsForm.setOpenPulCgdCount(reportsResponse.getOpenPulCgdCount());
        reportsForm.setSharedPulCgdCount(reportsResponse.getSharedPulCgdCount());
        reportsForm.setPrivatePulCgdCount(reportsResponse.getPrivatePulCgdCount());

        reportsForm.setOpenCulCgdCount(reportsResponse.getOpenCulCgdCount());
        reportsForm.setSharedCulCgdCount(reportsResponse.getSharedCulCgdCount());
        reportsForm.setPrivateCulCgdCount(reportsResponse.getPrivateCulCgdCount());

        reportsForm.setOpenNyplCgdCount(reportsResponse.getOpenNyplCgdCount());
        reportsForm.setSharedNyplCgdCount(reportsResponse.getSharedNyplCgdCount());
        reportsForm.setPrivateNyplCgdCount(reportsResponse.getPrivateNyplCgdCount());
    }

    public List<DeaccessionItemResultsRow> deaccessionReportFieldsInformation(ReportsForm reportsForm) throws Exception {
        ReportsResponse reportsResponse = reportsServiceUtil.requestDeaccessionResults(reportsForm);
        reportsForm.setTotalPageCount(reportsResponse.getTotalPageCount());
        reportsForm.setTotalRecordsCount(reportsResponse.getTotalRecordsCount());
        return reportsResponse.getDeaccessionItemResultsRows();
    }

    public List<IncompleteReportResultsRow> incompleteRecordsReportFieldsInformation(ReportsForm reportsForm) throws Exception {
        ReportsResponse reportsResponse = reportsServiceUtil.requestIncompleteRecords(reportsForm);
        if(!reportsForm.isExport()){
            reportsForm.setIncompleteTotalPageCount(reportsResponse.getIncompleteTotalPageCount());
            reportsForm.setIncompleteTotalRecordsCount(reportsResponse.getIncompleteTotalRecordsCount());
            reportsForm.setIncompletePageNumber(reportsForm.getIncompletePageNumber());
            reportsForm.setIncompletePageSize(reportsForm.getIncompletePageSize());
        }
        return reportsResponse.getIncompleteReportResultsRows();
    }

    public File exportIncompleteRecords(List<IncompleteReportResultsRow> incompleteReportResultsRows, String fileNameWithExtension) {
        File file = new File(fileNameWithExtension);
        CsvWriter csvOutput = null;
        if (CollectionUtils.isNotEmpty(incompleteReportResultsRows)){
            try (FileWriter fileWriter = new FileWriter(file)){
                csvOutput = new CsvWriter(fileWriter, ',');
                writeHeader(csvOutput);
                for (IncompleteReportResultsRow incompleteReportResultsRow : incompleteReportResultsRows) {
                    if(CollectionUtils.isNotEmpty(incompleteReportResultsRows)){
                        writeRow(incompleteReportResultsRow,csvOutput);
                    }
                }
            } catch (Exception e) {
                logger.error(RecapConstants.LOG_ERROR,e);
            }
            finally {
                if(csvOutput!=null) {
                    csvOutput.flush();
                    csvOutput.close();
                }
            }
        }
        return file;
    }

    private void writeRow(IncompleteReportResultsRow incompleteReportResultsRow, CsvWriter csvOutput) throws IOException {
        csvOutput.write(incompleteReportResultsRow.getOwningInstitution());
        csvOutput.write(incompleteReportResultsRow.getCustomerCode());
        csvOutput.write(incompleteReportResultsRow.getTitle());
        csvOutput.write(incompleteReportResultsRow.getAuthor());
        csvOutput.write(incompleteReportResultsRow.getBarcode());
        csvOutput.write(incompleteReportResultsRow.getCreatedDate());
        csvOutput.endRecord();
    }

    private void writeHeader(CsvWriter csvOutput) throws Exception{
        csvOutput.write("Owning Institution");
        csvOutput.write("Customer code");
        csvOutput.write("Title");
        csvOutput.write("Author");
        csvOutput.write("Barcode");
        csvOutput.write("Accession Date");
        csvOutput.endRecord();

    }
}
