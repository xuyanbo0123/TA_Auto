package name.mi.ckm.google;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201306.cm.*;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.BudgetHandler;
import name.mi.ckm.CKMException;
import name.mi.ckm.model.CampaignBudget;
import name.mi.ckm.dao.CampaignBudgetDAO;
import name.mi.micore.dao.TrafficProviderDAO;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.Date;

public class GoogleBudgetHandler extends AbstractGoogleCKMHandler
        implements BudgetHandler {
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleBudgetHandler.class);

    public GoogleBudgetHandler() throws CKMException
    {
        super();
    }

    // XYB:
    // Important New API Function
    // This function can be used to ADD or UPDATE budgets
    // The size of CampaignBudget[] should be <= 10
    public List<CampaignBudget> uploadBudgets(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, CampaignBudget... iCampaignBudgets) throws CKMException
    {
        // iCampaignBudgets can not be NULL
        if (iCampaignBudgets == null)
        {
            throw new CKMException("iCampaignBudgets is null");
        }

        if (iCampaignBudgets.length == 0 || iCampaignBudgets.length > 10)
        {
            throw new CKMException("length of iCampaignBudgets is 0 or greater than 10");
        }

        // Declaration
        List<CampaignBudget>
                vCampaignBudgetList = new ArrayList<CampaignBudget>();

        List<BudgetOperation>
                vBudgetOperationList = new ArrayList<BudgetOperation>();

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            for (CampaignBudget vCampaignBudget : iCampaignBudgets)
            {
                // vCampaignBudget should not be NULL
                if (vCampaignBudget == null)
                {
                    continue;
                }

                // Get vProviderSuppliedID
                long vProviderSuppliedID = vCampaignBudget.getProviderSuppliedID();

                // Judge whether budget is IN GOOGLE
                boolean vBudgetInGoogle = vProviderSuppliedID > 0;

                // ADD or UPDATE
                if (vBudgetInGoogle)
                {
                    vCampaignBudgetList.add(vCampaignBudget);

                    // Create a budget
                    Budget vBudget = new Budget();

                    // Set CUSTOMIZED information need to be UPDATED
                    vBudget.setBudgetId(vProviderSuppliedID);

                    vBudget.setName(vCampaignBudget.getName());

                    Money vMoney = new Money();
                    vMoney.setMicroAmount(GoogleUtils.toMicroAmount(vCampaignBudget.getAmount()));
                    vBudget.setAmount(vMoney);

                    vBudget.setPeriod(mapToBudgetBudgetPeriod(vCampaignBudget.getPeriod()));

                    vBudget.setDeliveryMethod(mapToBudgetBudgetDeliveryMethod(vCampaignBudget.getDeliveryMethod()));

                    // vBudgetOperation
                    BudgetOperation vBudgetOperation = new BudgetOperation();
                    vBudgetOperation.setOperand(vBudget);
                    vBudgetOperation.setOperator(Operator.SET);

                    vBudgetOperationList.add(vBudgetOperation);
                }
                else
                {
                    vCampaignBudgetList.add(vCampaignBudget);

                    // Create a budget
                    Budget vBudget = new Budget();

                    // Set CUSTOMIZED information need to be ADDED
                    vBudget.setName(vCampaignBudget.getName());

                    Money vMoney = new Money();
                    vMoney.setMicroAmount(GoogleUtils.toMicroAmount(vCampaignBudget.getAmount()));
                    vBudget.setAmount(vMoney);

                    vBudget.setPeriod(mapToBudgetBudgetPeriod(vCampaignBudget.getPeriod()));

                    vBudget.setDeliveryMethod(mapToBudgetBudgetDeliveryMethod(vCampaignBudget.getDeliveryMethod()));

                    // Set FORCED information need to be ADDED
                    vBudget.setIsExplicitlyShared(false);

                    // vBudgetOperation
                    BudgetOperation vBudgetOperation = new BudgetOperation();
                    vBudgetOperation.setOperand(vBudget);
                    vBudgetOperation.setOperator(Operator.ADD);

                    vBudgetOperationList.add(vBudgetOperation);
                }
            }

            // Create vBudgetService
            // Prepare to talk with GOOGLE
            BudgetServiceInterface vBudgetService =
                    iAdWordsServices.get(iAdWordsSession, BudgetServiceInterface.class);

            // Execute ADD or UPDATE
            int vSizeOfBudgetOperationList = vBudgetOperationList.size();
            BudgetReturnValue vBudgetReturnValue = vBudgetService.mutate(vBudgetOperationList.toArray(new BudgetOperation[vSizeOfBudgetOperationList]));

            // Get vGoogleReturnBudgets
            Budget[] vGoogleReturnBudgets = vBudgetReturnValue.getValue();

            // Verify whether vGoogleReturnBudgets is consistent with vCampaignBudgetList
            if (vGoogleReturnBudgets.length != vCampaignBudgetList.size())
            {
                throw new CKMException("vGoogleReturnBudgets is NOT consistent with vCampaignBudgetList");
            }

            // Update vCampaignBudgetList
            for (int i = 0; i < vCampaignBudgetList.size(); ++i)
            {
                if (vCampaignBudgetList.get(i).getProviderSuppliedID() <= 0)
                {
                    // Update provider_supplied_id
                    vCampaignBudgetList.get(i).setProviderSuppliedID(vGoogleReturnBudgets[i].getBudgetId());
                    CampaignBudgetDAO.updateCampaignBudgetProviderSuppliedIDByID(LOGGER, vConnection, vCampaignBudgetList.get(i).getID(), vGoogleReturnBudgets[i].getBudgetId());
                }
                vCampaignBudgetList.get(i).setProviderStatus(mapToStatus(vGoogleReturnBudgets[i].getStatus()));
                vCampaignBudgetList.get(i).setUpdatedTS(new Timestamp(new Date().getTime()));
                vCampaignBudgetList.get(i).setIsUploaded(true);
            }

            return vCampaignBudgetList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleBudgetHandler.uploadBudgets error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    // Important New API Function
    // This function can be used to get ALL budgets IN GOOGLE
    // Return List<CampaignBudget> where CampaignBudgetID and TrafficProviderID is faked
    public List<CampaignBudget> downloadBudgets(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            // Get the BudgetService.
            BudgetServiceInterface vBudgetService =
                    iAdWordsServices.get(iAdWordsSession, BudgetServiceInterface.class);

            // Create Selector.
            Selector vSelector = new Selector();

            // Set Fields
            vSelector.setFields(new String[]
                    {
                            "BudgetId",
                            "BudgetName",
                            "Period",
                            "Amount",
                            "DeliveryMethod",
                            "BudgetStatus"
                    });

            // Set Ordering
            OrderBy[] vOrdering = new OrderBy[]
                    {
                            new OrderBy("BudgetId", SortOrder.ASCENDING)
                    };
            vSelector.setOrdering(vOrdering);

            // Download all budgets
            // Max results in ONE PAGE is 100 required by GOOGLE: reasonable
            Paging vPaging;
            BudgetPage vBudgetPage;
            Budget[] vBudgets;
            List<Budget> vBudgetList = new ArrayList<Budget>();
            int vPageNumber = 0;
            boolean vComplete = false;
            while (!vComplete)
            {
                vPaging = new Paging();
                vPaging.setNumberResults(100);
                vPaging.setStartIndex(100 * vPageNumber);
                vSelector.setPaging(vPaging);

                vBudgetPage = vBudgetService.get(vSelector);
                vBudgets = vBudgetPage.getEntries();

                if (vBudgets == null)
                {
                    vComplete = true;
                }
                else
                {
                    vBudgetList.addAll(Arrays.asList(vBudgets));
                }

                vPageNumber++;
            }

            List<CampaignBudget> vCampaignBudgetList = new ArrayList<CampaignBudget>();
            for (Budget vBudget : vBudgetList)
            {
                vCampaignBudgetList.add(
                        new CampaignBudget
                                (
                                        -1,
                                        new java.util.Date(),
                                        new java.util.Date(),
                                        TrafficProviderDAO.getTrafficProviderByName(LOGGER, vConnection, AbstractGoogleCKMHandler.PROVIDER_NAME_GOOGLE).getID(),
                                        vBudget.getBudgetId(),
                                        vBudget.getName(),
                                        mapToStatus(vBudget.getStatus()),
                                        mapToStatus(vBudget.getStatus()),
                                        mapToCampaignBudgetPeriod(vBudget.getPeriod()),
                                        mapToCampaignBudgetDeliveryMethod(vBudget.getDeliveryMethod()),
                                        GoogleUtils.toCents(vBudget.getAmount().getMicroAmount()),
                                        new Timestamp(new java.util.Date().getTime()),
                                        true
                                )
                );
            }

            return vCampaignBudgetList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleBudgetHandler.downloadBudgets error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    private static CampaignBudget.Status mapToStatus(BudgetBudgetStatus iBudgetBudgetStatus) throws CKMException
    {
        if (BudgetBudgetStatus.ACTIVE.equals(iBudgetBudgetStatus))
        {
            return CampaignBudget.Status.active;
        }

        if (BudgetBudgetStatus.DELETED.equals(iBudgetBudgetStatus))
        {
            return CampaignBudget.Status.deleted;
        }

        if (BudgetBudgetStatus.UNKNOWN.equals(iBudgetBudgetStatus))
        {
            return CampaignBudget.Status.unknown;
        }

        throw new CKMException("Unknown BudgetBudgetStatus " + iBudgetBudgetStatus);
    }

    // XYB:
    private static BudgetBudgetPeriod mapToBudgetBudgetPeriod(CampaignBudget.Period iPeriod) throws CKMException
    {
        switch (iPeriod)
        {
            case daily:
                return BudgetBudgetPeriod.DAILY;

            default:
                throw new CKMException("Unknown CampaignBudget.Period " + iPeriod);
        }
    }

    // XYB:
    private static CampaignBudget.Period mapToCampaignBudgetPeriod(BudgetBudgetPeriod iBudgetBudgetPeriod) throws CKMException
    {
        if (BudgetBudgetPeriod.DAILY.equals(iBudgetBudgetPeriod))
        {
            return CampaignBudget.Period.daily;
        }

        throw new CKMException("Unknown BudgetBudgetPeriod " + iBudgetBudgetPeriod);
    }

    // XYB:
    private static BudgetBudgetDeliveryMethod mapToBudgetBudgetDeliveryMethod(CampaignBudget.DeliveryMethod iDeliveryMethod) throws CKMException
    {
        switch (iDeliveryMethod)
        {
            case standard:
                return BudgetBudgetDeliveryMethod.STANDARD;

            case accelerated:
                return BudgetBudgetDeliveryMethod.ACCELERATED;

            default:
                throw new CKMException("Unknown CampaignBudget.DeliveryMethod " + iDeliveryMethod);
        }
    }

    // XYB:
    private static CampaignBudget.DeliveryMethod mapToCampaignBudgetDeliveryMethod(BudgetBudgetDeliveryMethod iBudgetBudgetDeliveryMethod) throws CKMException
    {
        if (BudgetBudgetDeliveryMethod.STANDARD.equals(iBudgetBudgetDeliveryMethod))
        {
            return CampaignBudget.DeliveryMethod.standard;
        }

        if (BudgetBudgetDeliveryMethod.ACCELERATED.equals(iBudgetBudgetDeliveryMethod))
        {
            return CampaignBudget.DeliveryMethod.accelerated;
        }

        throw new CKMException("Unknown BudgetBudgetDeliveryMethod " + iBudgetBudgetDeliveryMethod);
    }
}
