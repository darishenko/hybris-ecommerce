package de.hybris.training.core.job;

import questions.model.QuestionModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.training.core.model.NewQuestionsEmailProcessModel;
import de.hybris.training.core.services.QuestionService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobHistoryModel;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.CronJobHistoryService;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class NewQuestionsEmailSendJob extends AbstractJobPerformable<CronJobModel> {
    private static final String NEW_QUESTIONS_PROCESS_NAME = "newQuestionsEmailProcess";
    private static final String BASE_SITE = "electronics";
    private static final String LOGGER_LAST_EXECUTE_TIME = "Last execute time: %s";
    private static final String LOGGER_NEW_QUESTIONS_COUNT = "New questions count: %d";
    private static final Logger LOGGER = Logger.getLogger(NewQuestionsEmailSendJob.class);

    private CronJobHistoryService cronJobHistoryService;
    private BusinessProcessService businessProcessService;
    private BaseSiteService baseSiteService;
    private ModelService modelService;
    private QuestionService questionService;

    @Override
    public PerformResult perform(@Nonnull CronJobModel cronJob) {
        NewQuestionsEmailProcessModel process = populateNewQuestionsEmailProcessModel();
        Set<QuestionModel> questions = getNewQuestions(cronJob);

        if (CollectionUtils.isEmpty(questions)) {
            return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
        }

        process.setQuestions(questions);

        LOGGER.info(String.format(LOGGER_NEW_QUESTIONS_COUNT, questions.size()));

        modelService.save(process);
        businessProcessService.startProcess(process);

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private Set<QuestionModel> getNewQuestions(CronJobModel cronJob) {
        final Date lastExecuteTime = getLastSuccessCronJobExecuteTime(cronJob);

        LOGGER.info(String.format(LOGGER_LAST_EXECUTE_TIME, lastExecuteTime));

        return new HashSet<>(questionService.getQuestionsCreatedAfterTime(lastExecuteTime));
    }

    @Nullable
    private Date getLastSuccessCronJobExecuteTime(CronJobModel cronJob) {
        return cronJobHistoryService.getCronJobHistoryBy(cronJob.getCode()).stream()
                .filter(job -> CronJobStatus.FINISHED.equals(job.getStatus()))
                .filter(job -> CronJobResult.SUCCESS.equals(job.getResult()))
                .max(Comparator.comparing(CronJobHistoryModel::getEndTime))
                .map(CronJobHistoryModel::getEndTime)
                .orElse(null);
    }

    private NewQuestionsEmailProcessModel populateNewQuestionsEmailProcessModel() {
        NewQuestionsEmailProcessModel process = businessProcessService
                .createProcess(NEW_QUESTIONS_PROCESS_NAME + System.currentTimeMillis(), NEW_QUESTIONS_PROCESS_NAME);
        BaseSiteModel baseSite = baseSiteService.getBaseSiteForUID(BASE_SITE);
        List<BaseStoreModel> baseStoreList = baseSite.getStores();

        process.setSite(baseSite);
        process.setLanguage(baseSite.getDefaultLanguage());

        if (CollectionUtils.isNotEmpty(baseStoreList)) {
            process.setStore(baseSite.getStores().get(0));
        }

        return process;
    }

    @Required
    public void setQuestionService(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @Required
    public void setCronJobHistoryService(final CronJobHistoryService cronJobHistoryService) {
        this.cronJobHistoryService = cronJobHistoryService;
    }

    @Required
    public void setBusinessProcessService(final BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    @Required
    public void setBaseSiteService(final BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }

    @Required
    public void setModelService(final ModelService modelService) {
        this.modelService = modelService;
    }

}
