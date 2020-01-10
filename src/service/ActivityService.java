package service;

import model.Activity;
import repository.ActivityRepository;

import java.util.ArrayList;

public class ActivityService {

    private ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public void addActivity(Activity activity) {
        activityRepository.getActivities().add(activity);
        activityRepository.save();
    }

    public ArrayList<Activity> getActivities() {
        return activityRepository.getActivities();
    }

    public void save() {
        activityRepository.save();
    }

    public ArrayList<Activity> getActivitiesTeacherId(Integer idTeacher) {
        ArrayList<Activity> activitiesTeacherId = new ArrayList<Activity>();
        for (int l = 0; l < getActivities().size(); l++) {
            if (getActivities().get(l).getIdTeacher() == idTeacher) {
                activitiesTeacherId.add(getActivities().get(l));
            }
        }
        return activitiesTeacherId;
    }
}