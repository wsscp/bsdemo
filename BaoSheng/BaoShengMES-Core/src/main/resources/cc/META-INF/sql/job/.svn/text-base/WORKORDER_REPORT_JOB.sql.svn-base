-- 生产单记录报表定时任务-job
begin
  sys.dbms_scheduler.create_job(job_name            => 'WORKORDER_REPORT_JOB',
                                job_type            => 'STORED_PROCEDURE',
                                job_action          => 'WORKORDER_REPORT',
                                number_of_arguments => 4,
                                start_date          => to_date('08-08-2014 00:00:22',
                                                               'dd-mm-yyyy hh24:mi:ss'),
                                repeat_interval     => 'Freq=Daily;ByHour=23;ByMinute=55;BySecond=00',
                                end_date            => to_date(null),
                                job_class           => 'DEFAULT_JOB_CLASS',
                                enabled             => false,
                                auto_drop           => false,
                                comments            => '');
  sys.dbms_scheduler.set_job_argument_value(job_name          => 'WORKORDER_REPORT_JOB',
                                            argument_position => 1,
                                            argument_value    => '');
  sys.dbms_scheduler.set_job_argument_value(job_name          => 'WORKORDER_REPORT_JOB',
                                            argument_position => 2,
                                            argument_value    => '');
  sys.dbms_scheduler.set_job_argument_value(job_name          => 'WORKORDER_REPORT_JOB',
                                            argument_position => 3,
                                            argument_value    => '');
  sys.dbms_scheduler.set_job_argument_value(job_name          => 'WORKORDER_REPORT_JOB',
                                            argument_position => 4,
                                            argument_value    => '');
  sys.dbms_scheduler.enable(name => 'WORKORDER_REPORT_JOB');
end;