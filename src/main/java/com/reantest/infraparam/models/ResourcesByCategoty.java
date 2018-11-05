package com.reantest.infraparam.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcesByCategoty {
	@SuppressWarnings("serial")
	private static Map<String, List<String>> map = new HashMap<String, List<String>>() {
		{
			put("VPC",
					Arrays.asList("aws_vpc", "aws_subnet", "aws_route_table", "aws_security_group", "aws_network_acl",
							"aws_nat_gateway", "aws_route_table_association", "aws_internet_gateway",
							"aws_vpc_endpoint", "aws_flow_log", "aws_vpc_peering_connection", "aws_customer_gateway",
							"aws_vpn_gateway", "aws_vpn_connection", "aws_vpn_connection_route"));
			put("EC2",
					Arrays.asList("aws_instance", "aws_ami", "aws_autoscaling_group", "aws_launch_configuration",
							"aws_eip", "aws_elb", "aws_alb", "aws_lb_target_group", "aws_alb_listener",
							"aws_alb_listener_rule", "aws_elb_load_balancer_listener_policy"));
			put("EFS", Arrays.asList("aws_efs_file_system", "aws_efs_mount_target"));
			put("IAM", Arrays.asList("aws_iam_group", "aws_iam_group_policy", "aws_iam_role", "aws_iam_policy",
					"aws_iam_role_policy", "aws_iam_instance_profile"));
			put("Other_Services", Arrays.asList("aws_cloudwatch_log_group"));
			put("SNS", Arrays.asList("aws_sns_topic", "aws_sns_topic_subscription", "aws_sns_topic_policy"));
			put("S3", Arrays.asList("aws_s3_bucket"));
			put("RDS", Arrays.asList("aws_db_option_group", "aws_db_parameter_group", "aws_db_subnet_group",
					"aws_db_instance", "aws_rds_cluster", "aws_rds_cluster_instance"));
			put("SES",
					Arrays.asList("aws_ses_active_receipt_rule_set", "aws_ses_domain_identity",
							"aws_ses_domain_identity_verification", "aws_ses_receipt_filter", "aws_ses_receipt_rule",
							"aws_ses_receipt_rule_set", "aws_ses_configuration_set", "aws_ses_event_destination"));
			put("SQS", Arrays.asList("aws_sqs_queue", "aws_sqs_queue_policy"));
			put("Dynamodb", Arrays.asList("aws_dynamodb_table"));
			put("Route53", Arrays.asList("aws_route53_health_check", "aws_route53_record", "aws_route53_zone"));
		}
	};

	public static Map<String, List<String>> map() {
		return map;
	}

	public static String getKeyIfValueExist(String value) {
		for (String key : map.keySet()) {
			List<String> values = map.get(key);
			if (values.contains(value)) {
				return key;
			}
		}
		return "Other_Services";
	}
}
