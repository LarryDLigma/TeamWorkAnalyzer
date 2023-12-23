class WorkedTogetherResult {
    private Long projectId;
    private Long employee1;
    private Long employee2;
    private Long totalDaysWorked;

    public WorkedTogetherResult(Long projectId, Long employee1, Long employee2, Long totalDaysWorked) {
        this.projectId = projectId;
        this.employee1 = employee1;
        this.employee2 = employee2;
        this.totalDaysWorked = totalDaysWorked;
    }

    @Override
    public String toString() {
        return "ProjectID: " + projectId +
                ", Employee1: " + employee1 +
                ", Employee2: " + employee2 +
                ", Total Days Worked: " + totalDaysWorked;
    }
}
