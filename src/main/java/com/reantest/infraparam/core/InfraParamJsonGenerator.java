package com.reantest.infraparam.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.NullNode;

import com.reantest.infraparam.models.InfraParam;
import com.reantest.infraparam.models.ResourcesByCategoty;
import com.reantest.infraparam.models.TFResource;
import com.reantest.infraparam.utils.Utils;

/**
 * 
 * @author tahir
 *
 */
public class InfraParamJsonGenerator {
	private final static Map<String, Map<String, Map<String, Object>>> input = new HashMap<String, Map<String, Map<String, Object>>>();
	private final static Map<String, Map<String, String>> output = new HashMap<String, Map<String, String>>();

	public static void generateInfraParamJson(String tfStateFilePath, String infraParamOutputPath) {
		System.out.println("Started infraParam.json generation from " + tfStateFilePath);
		Map<String, TFResource> resources = parseTfState(tfStateFilePath);
		if (resources != null) {
			for (String k : resources.keySet()) {
				TFResource tfResource = resources.get(k);
				String category = ResourcesByCategoty.getKeyIfValueExist(tfResource.getType());
				if (input.get(category) == null) {
					input.put(category, new HashMap<String, Map<String, Object>>());
				}
				input.get(category).put(tfResource.getName(), tfResource.getAttributes());

				if (output.get(tfResource.getType()) == null)
					output.put(tfResource.getType(), new HashMap<String, String>());

				output.get(tfResource.getType()).put(tfResource.getName(), tfResource.getId());
			}
			InfraParam infraParam = new InfraParam(input, output);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				objectMapper.writerWithDefaultPrettyPrinter()
						.writeValue(Paths.get(infraParamOutputPath, "infraParam.json").toFile(), infraParam);
				System.out.println("infraParam.json generated at " + infraParamOutputPath);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static Map<String, TFResource> parseTfState(String tfStateFilePath) {
		System.out.println("Started parsing of AWS resources.");
		Map<String, TFResource> allResources = new HashMap<String, TFResource>();
		Map<String, Map<String, Object>> nestedMap = null;
		JsonNode jsonNode = null;
		File file = new File(tfStateFilePath);
		if (file.exists()) {
			jsonNode = Utils.readJsonFromFileAsTree(file.getPath());
		}
		if (jsonNode != null) {
			JsonNode modulesNode = jsonNode.path("modules");
			Iterator<JsonNode> modules = modulesNode.getElements();
			while (modules.hasNext()) {
				JsonNode moduleNode = modules.next();
				JsonNode resources = moduleNode.path("resources");
				Iterator<String> fieldNames = resources.getFieldNames();
				while (fieldNames.hasNext()) {
					String resourceName = fieldNames.next();
					TFResource tfResource = new TFResource();
					tfResource.setName(resourceName);
					allResources.put(resourceName, tfResource);
					JsonNode resourceNode = resources.get(resourceName);
					String type = resourceNode.get("type").getTextValue();
					tfResource.setType(type);
					System.out.println("Parsing AWS Resource : Name- " + resourceName + ", Type- " + type);
					JsonNode primaryNode = resourceNode.get("primary");
					JsonNode taintedNode = resourceNode.get("tainted");
					if (primaryNode instanceof NullNode) {
						ArrayNode an = (ArrayNode) taintedNode;
						primaryNode = an.get(0);
					}
					if (primaryNode == null) {
						continue;
					}
					String id = primaryNode.get("id").getTextValue();
					JsonNode attributes = primaryNode.get("attributes");
					if (attributes == null) {
						continue;
					}
					String percentKey = null;
					String hashKey = null;
					Iterator<String> attributeNames = attributes.getFieldNames();
					boolean moveNext = true;
					String key = null;
					String value = null;
					while (attributeNames.hasNext()) {

						if (moveNext) {
							key = attributeNames.next();
							value = attributes.get(key).getTextValue();
						}
						moveNext = true;
						if (key.contains(".%")) {
							percentKey = null;
							percentKey = key.split("\\.")[0];
							nestedMap = new HashMap<String, Map<String, Object>>();
							nestedMap.put(percentKey, new HashMap<String, Object>());
							try {
								int size = Integer.parseInt(value);
								for (int i = 0; i < size; i++) {
									if (attributeNames.hasNext()) {
										String nextKey = attributeNames.next();
										String nextValue = attributes.get(nextKey).getTextValue();
										nestedMap.get(percentKey).put(nextKey.split("\\.")[1], nextValue);
										if (i == (size - 1))
											tfResource.addAttribute(percentKey, nestedMap.get(percentKey));
									}
								}
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
							moveNext = true;
						} else if (key.contains(".#")) {
							hashKey = null;
							hashKey = key.split("\\.")[0];
							Map<String, Object> m = new HashMap<String, Object>();
							List<String> l = new LinkedList<String>();
							List<Map<String, Object>> lm = new LinkedList<Map<String, Object>>();
							try {
								int size = Integer.parseInt(value);
								for (int i = 0; i < size; i++) {
									String nextKey = null;
									String nextValue = null;
									String hashKeyIndex = null;
									do {
										if (attributeNames.hasNext()) {
											nextKey = attributeNames.next();
											nextValue = attributes.get(nextKey).getTextValue();
											if (nextKey.contains(".#") || nextKey.contains(".%")) {
												key = nextKey;
												value = nextValue;
												moveNext = false;
												break;
											}

											String[] split = nextKey.split("\\.");
											hashKeyIndex = "temp";
											if (split != null && split.length == 3) {
												m.put(split[2], nextValue);
												hashKeyIndex = split[1];
											} else if (split != null && split.length == 2) {
												l.add(nextValue);
												hashKeyIndex = split[1];
											} else if (split != null && split.length == 1) {
												tfResource.addAttribute(split[0], nextValue);
											}
										} else {
											break;
										}
									} while ((nextKey != null && nextKey.startsWith(hashKey + "." + hashKeyIndex)));
									if (m != null && m.size() > 0)
										lm.add(m);
									if (!moveNext)
										break;
								}
								if (lm != null && lm.size() > 0) {
									tfResource.addAttribute(hashKey, lm);
								} else if (l != null && l.size() > 0) {
									tfResource.addAttribute(hashKey, l);
								}
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						} else {
							tfResource.addAttribute(key, value);
							moveNext = true;
						}

					}
					tfResource.setId(id);
				}
			}
		}
		System.out.println("All AWS resources parsed successfully.");
		return allResources;
	}
}
