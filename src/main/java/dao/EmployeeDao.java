package dao;

import fa.training.entity.Candidate;
import fa.training.entity.enumeration.Result;

import java.time.LocalDate;
import java.util.List;

public interface CandidateDao extends EntityDao<Candidate, Integer> {
    @Override
    default Class<Candidate> getEntityClass() {
        return Candidate.class;
    }

    List<Candidate> getBySkillAndLevel(String skill, int level);
    List<Candidate> getByForeignLanguageAndSkill(String foreignLanguage, String skill);
    List<Candidate> getBySkillAndResultAndDateEntryTest(String skill, Result testResult, LocalDate dateEntryTest);
    List<Candidate> getByResultAndDateInterview(Result interviewResult, LocalDate dateInterview);

    /**
     * Update remark is inactive for candidates who do not have either phone, email and cv.
     * */
    boolean updateRemarkIsInactive();
    List<Candidate> getAllWithPaging(int pageNumber, int pageSize);
}
