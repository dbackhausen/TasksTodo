package org.taskstodo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.taskstodo.model.Goal;

@Repository
public class GoalDAOImpl extends GenericDaoImpl<Goal> implements GoalDAO {
  @Autowired
  private MongoTemplate mongoTemplate;

  public GoalDAOImpl() {
    super(Goal.class);
  }
}
