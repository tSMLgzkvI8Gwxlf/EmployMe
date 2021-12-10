package dte.employme.board;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;

import dte.employme.job.Job;

public interface JobBoard extends Iterable<Job>
{
	void addJob(Job job);
	void removeJob(Job job);
	void completeJob(Job job, Player whoCompleted);
	
	//jobs query
	List<Job> getOfferedJobs();
	List<Job> getJobsOfferedBy(UUID employerUUID);
	Optional<Job> getJobByID(String id);
	Optional<String> getJobID(Job job);
}