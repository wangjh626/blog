package com.wangjh.blog.entity;

import java.util.ArrayList;
import java.util.List;

public class CommentExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public CommentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPIdIsNull() {
            addCriterion("p_id is null");
            return (Criteria) this;
        }

        public Criteria andPIdIsNotNull() {
            addCriterion("p_id is not null");
            return (Criteria) this;
        }

        public Criteria andPIdEqualTo(Long value) {
            addCriterion("p_id =", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotEqualTo(Long value) {
            addCriterion("p_id <>", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThan(Long value) {
            addCriterion("p_id >", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThanOrEqualTo(Long value) {
            addCriterion("p_id >=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThan(Long value) {
            addCriterion("p_id <", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThanOrEqualTo(Long value) {
            addCriterion("p_id <=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdIn(List<Long> values) {
            addCriterion("p_id in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotIn(List<Long> values) {
            addCriterion("p_id not in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdBetween(Long value1, Long value2) {
            addCriterion("p_id between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotBetween(Long value1, Long value2) {
            addCriterion("p_id not between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andArticleIdIsNull() {
            addCriterion("article_id is null");
            return (Criteria) this;
        }

        public Criteria andArticleIdIsNotNull() {
            addCriterion("article_id is not null");
            return (Criteria) this;
        }

        public Criteria andArticleIdEqualTo(Long value) {
            addCriterion("article_id =", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotEqualTo(Long value) {
            addCriterion("article_id <>", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdGreaterThan(Long value) {
            addCriterion("article_id >", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("article_id >=", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLessThan(Long value) {
            addCriterion("article_id <", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLessThanOrEqualTo(Long value) {
            addCriterion("article_id <=", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdIn(List<Long> values) {
            addCriterion("article_id in", values, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotIn(List<Long> values) {
            addCriterion("article_id not in", values, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdBetween(Long value1, Long value2) {
            addCriterion("article_id between", value1, value2, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotBetween(Long value1, Long value2) {
            addCriterion("article_id not between", value1, value2, "articleId");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorIsNull() {
            addCriterion("original_author is null");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorIsNotNull() {
            addCriterion("original_author is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorEqualTo(String value) {
            addCriterion("original_author =", value, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorNotEqualTo(String value) {
            addCriterion("original_author <>", value, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorGreaterThan(String value) {
            addCriterion("original_author >", value, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorGreaterThanOrEqualTo(String value) {
            addCriterion("original_author >=", value, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorLessThan(String value) {
            addCriterion("original_author <", value, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorLessThanOrEqualTo(String value) {
            addCriterion("original_author <=", value, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorLike(String value) {
            addCriterion("original_author like", value, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorNotLike(String value) {
            addCriterion("original_author not like", value, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorIn(List<String> values) {
            addCriterion("original_author in", values, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorNotIn(List<String> values) {
            addCriterion("original_author not in", values, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorBetween(String value1, String value2) {
            addCriterion("original_author between", value1, value2, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andOriginalAuthorNotBetween(String value1, String value2) {
            addCriterion("original_author not between", value1, value2, "originalAuthor");
            return (Criteria) this;
        }

        public Criteria andAnswererIdIsNull() {
            addCriterion("answerer_id is null");
            return (Criteria) this;
        }

        public Criteria andAnswererIdIsNotNull() {
            addCriterion("answerer_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnswererIdEqualTo(Integer value) {
            addCriterion("answerer_id =", value, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererIdNotEqualTo(Integer value) {
            addCriterion("answerer_id <>", value, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererIdGreaterThan(Integer value) {
            addCriterion("answerer_id >", value, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("answerer_id >=", value, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererIdLessThan(Integer value) {
            addCriterion("answerer_id <", value, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererIdLessThanOrEqualTo(Integer value) {
            addCriterion("answerer_id <=", value, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererIdIn(List<Integer> values) {
            addCriterion("answerer_id in", values, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererIdNotIn(List<Integer> values) {
            addCriterion("answerer_id not in", values, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererIdBetween(Integer value1, Integer value2) {
            addCriterion("answerer_id between", value1, value2, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererIdNotBetween(Integer value1, Integer value2) {
            addCriterion("answerer_id not between", value1, value2, "answererId");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameIsNull() {
            addCriterion("answerer_username is null");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameIsNotNull() {
            addCriterion("answerer_username is not null");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameEqualTo(String value) {
            addCriterion("answerer_username =", value, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameNotEqualTo(String value) {
            addCriterion("answerer_username <>", value, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameGreaterThan(String value) {
            addCriterion("answerer_username >", value, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("answerer_username >=", value, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameLessThan(String value) {
            addCriterion("answerer_username <", value, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameLessThanOrEqualTo(String value) {
            addCriterion("answerer_username <=", value, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameLike(String value) {
            addCriterion("answerer_username like", value, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameNotLike(String value) {
            addCriterion("answerer_username not like", value, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameIn(List<String> values) {
            addCriterion("answerer_username in", values, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameNotIn(List<String> values) {
            addCriterion("answerer_username not in", values, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameBetween(String value1, String value2) {
            addCriterion("answerer_username between", value1, value2, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andAnswererUsernameNotBetween(String value1, String value2) {
            addCriterion("answerer_username not between", value1, value2, "answererUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentIdIsNull() {
            addCriterion("respondent_id is null");
            return (Criteria) this;
        }

        public Criteria andRespondentIdIsNotNull() {
            addCriterion("respondent_id is not null");
            return (Criteria) this;
        }

        public Criteria andRespondentIdEqualTo(Integer value) {
            addCriterion("respondent_id =", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdNotEqualTo(Integer value) {
            addCriterion("respondent_id <>", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdGreaterThan(Integer value) {
            addCriterion("respondent_id >", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("respondent_id >=", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdLessThan(Integer value) {
            addCriterion("respondent_id <", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdLessThanOrEqualTo(Integer value) {
            addCriterion("respondent_id <=", value, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdIn(List<Integer> values) {
            addCriterion("respondent_id in", values, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdNotIn(List<Integer> values) {
            addCriterion("respondent_id not in", values, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdBetween(Integer value1, Integer value2) {
            addCriterion("respondent_id between", value1, value2, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("respondent_id not between", value1, value2, "respondentId");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameIsNull() {
            addCriterion("respondent_username is null");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameIsNotNull() {
            addCriterion("respondent_username is not null");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameEqualTo(String value) {
            addCriterion("respondent_username =", value, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameNotEqualTo(String value) {
            addCriterion("respondent_username <>", value, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameGreaterThan(String value) {
            addCriterion("respondent_username >", value, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("respondent_username >=", value, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameLessThan(String value) {
            addCriterion("respondent_username <", value, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameLessThanOrEqualTo(String value) {
            addCriterion("respondent_username <=", value, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameLike(String value) {
            addCriterion("respondent_username like", value, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameNotLike(String value) {
            addCriterion("respondent_username not like", value, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameIn(List<String> values) {
            addCriterion("respondent_username in", values, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameNotIn(List<String> values) {
            addCriterion("respondent_username not in", values, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameBetween(String value1, String value2) {
            addCriterion("respondent_username between", value1, value2, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andRespondentUsernameNotBetween(String value1, String value2) {
            addCriterion("respondent_username not between", value1, value2, "respondentUsername");
            return (Criteria) this;
        }

        public Criteria andCommentDateIsNull() {
            addCriterion("comment_date is null");
            return (Criteria) this;
        }

        public Criteria andCommentDateIsNotNull() {
            addCriterion("comment_date is not null");
            return (Criteria) this;
        }

        public Criteria andCommentDateEqualTo(Long value) {
            addCriterion("comment_date =", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateNotEqualTo(Long value) {
            addCriterion("comment_date <>", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateGreaterThan(Long value) {
            addCriterion("comment_date >", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateGreaterThanOrEqualTo(Long value) {
            addCriterion("comment_date >=", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateLessThan(Long value) {
            addCriterion("comment_date <", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateLessThanOrEqualTo(Long value) {
            addCriterion("comment_date <=", value, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateIn(List<Long> values) {
            addCriterion("comment_date in", values, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateNotIn(List<Long> values) {
            addCriterion("comment_date not in", values, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateBetween(Long value1, Long value2) {
            addCriterion("comment_date between", value1, value2, "commentDate");
            return (Criteria) this;
        }

        public Criteria andCommentDateNotBetween(Long value1, Long value2) {
            addCriterion("comment_date not between", value1, value2, "commentDate");
            return (Criteria) this;
        }

        public Criteria andLikesIsNull() {
            addCriterion("likes is null");
            return (Criteria) this;
        }

        public Criteria andLikesIsNotNull() {
            addCriterion("likes is not null");
            return (Criteria) this;
        }

        public Criteria andLikesEqualTo(Integer value) {
            addCriterion("likes =", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesNotEqualTo(Integer value) {
            addCriterion("likes <>", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesGreaterThan(Integer value) {
            addCriterion("likes >", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesGreaterThanOrEqualTo(Integer value) {
            addCriterion("likes >=", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesLessThan(Integer value) {
            addCriterion("likes <", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesLessThanOrEqualTo(Integer value) {
            addCriterion("likes <=", value, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesIn(List<Integer> values) {
            addCriterion("likes in", values, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesNotIn(List<Integer> values) {
            addCriterion("likes not in", values, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesBetween(Integer value1, Integer value2) {
            addCriterion("likes between", value1, value2, "likes");
            return (Criteria) this;
        }

        public Criteria andLikesNotBetween(Integer value1, Integer value2) {
            addCriterion("likes not between", value1, value2, "likes");
            return (Criteria) this;
        }

        public Criteria andCommentContentIsNull() {
            addCriterion("comment_content is null");
            return (Criteria) this;
        }

        public Criteria andCommentContentIsNotNull() {
            addCriterion("comment_content is not null");
            return (Criteria) this;
        }

        public Criteria andCommentContentEqualTo(String value) {
            addCriterion("comment_content =", value, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentNotEqualTo(String value) {
            addCriterion("comment_content <>", value, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentGreaterThan(String value) {
            addCriterion("comment_content >", value, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentGreaterThanOrEqualTo(String value) {
            addCriterion("comment_content >=", value, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentLessThan(String value) {
            addCriterion("comment_content <", value, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentLessThanOrEqualTo(String value) {
            addCriterion("comment_content <=", value, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentLike(String value) {
            addCriterion("comment_content like", value, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentNotLike(String value) {
            addCriterion("comment_content not like", value, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentIn(List<String> values) {
            addCriterion("comment_content in", values, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentNotIn(List<String> values) {
            addCriterion("comment_content not in", values, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentBetween(String value1, String value2) {
            addCriterion("comment_content between", value1, value2, "commentContent");
            return (Criteria) this;
        }

        public Criteria andCommentContentNotBetween(String value1, String value2) {
            addCriterion("comment_content not between", value1, value2, "commentContent");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table comment_record
     *
     * @mbg.generated do_not_delete_during_merge Mon Aug 05 15:02:58 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table comment_record
     *
     * @mbg.generated Mon Aug 05 15:02:58 CST 2019
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}