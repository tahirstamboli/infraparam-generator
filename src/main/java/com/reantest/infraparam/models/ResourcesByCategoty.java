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
							"aws_vpc_endpoint", "aws_flow_log"));
			put("EC2", Arrays.asList("aws_instance", "aws_ami", "aws_autoscaling_group", "aws_launch_configuration",
					"aws_eip", "aws_elb", "aws_alb", "aws_lb_target_group"));
			put("EFS", Arrays.asList("aws_efs_file_system", "aws_efs_mount_target"));
			put("IAM", Arrays.asList("aws_iam_group", "aws_iam_group_policy", "aws_iam_role", "aws_iam_policy",
					"aws_iam_role_policy", "aws_iam_instance_profile"));
			put("Other_Services", Arrays.asList("aws_cloudwatch_log_group"));
			put("SNS", Arrays.asList("aws_sns_topic", "aws_sns_topic_subscription"));
			put("S3", Arrays.asList("aws_s3_bucket"));

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
