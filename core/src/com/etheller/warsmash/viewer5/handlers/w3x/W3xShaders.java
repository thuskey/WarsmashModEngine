package com.etheller.warsmash.viewer5.handlers.w3x;

public class W3xShaders {
	public static final class UberSplat {
		private UberSplat() {
		}

		public static final String vert = "\r\n" + //
				"    uniform mat4 u_mvp;\r\n" + //
				"    uniform sampler2D u_heightMap;\r\n" + //
				"    uniform vec2 u_pixel;\r\n" + //
				"    uniform vec2 u_size;\r\n" + //
				"    uniform vec2 u_shadowPixel;\r\n" + //
				"    uniform vec2 u_centerOffset;\r\n" + //
				"    attribute vec3 a_position;\r\n" + //
				"    attribute vec2 a_uv;\r\n" + //
				"    varying vec2 v_uv;\r\n" + //
				"    varying vec2 v_suv;\r\n" + //
				"    varying vec3 v_normal;\r\n" + //
				"    const float normalDist = 0.25;\r\n" + //
				"    void main() {\r\n" + //
				"      vec2 halfPixel = u_pixel * 0.5;\r\n" + //
				"      vec2 base = (a_position.xy - u_centerOffset) / 128.0;\r\n" + //
				"      float height = texture2D(u_heightMap, base * u_pixel + halfPixel).r;\r\n" + //
				"      float hL = texture2D(u_heightMap, vec2(base - vec2(normalDist, 0.0)) * u_pixel + halfPixel).r;\r\n"
				+ //
				"      float hR = texture2D(u_heightMap, vec2(base + vec2(normalDist, 0.0)) * u_pixel + halfPixel).r;\r\n"
				+ //
				"      float hD = texture2D(u_heightMap, vec2(base - vec2(0.0, normalDist)) * u_pixel + halfPixel).r;\r\n"
				+ //
				"      float hU = texture2D(u_heightMap, vec2(base + vec2(0.0, normalDist)) * u_pixel + halfPixel).r;\r\n"
				+ //
				"      v_normal = normalize(vec3(hL - hR, hD - hU, normalDist * 2.0));\r\n" + //
				"      v_uv = a_uv;\r\n" + //
				"      v_suv = base / u_size;\r\n" + //
				"      gl_Position = u_mvp * vec4(a_position.xy, height * 128.0 + a_position.z, 1.0);\r\n" + //
				"    }\r\n" + //
				" ";

		public static final String frag = "\r\n" + //
				"    uniform sampler2D u_texture;\r\n" + //
				"    uniform sampler2D u_shadowMap;\r\n" + //
				"    uniform vec4 u_color;\r\n" + //
				"    varying vec2 v_uv;\r\n" + //
				"    varying vec2 v_suv;\r\n" + //
				"    varying vec3 v_normal;\r\n" + //
				"    const vec3 lightDirection = normalize(vec3(-0.3, -0.3, 0.25));\r\n" + //
				"    void main() {\r\n" + //
				"      if (any(bvec4(lessThan(v_uv, vec2(0.0)), greaterThan(v_uv, vec2(1.0))))) {\r\n" + //
				"        discard;\r\n" + //
				"      }\r\n" + //
				"      vec4 color = texture2D(u_texture, clamp(v_uv, 0.0, 1.0)).rgba * u_color;\r\n" + //
				"      float shadow = texture2D(u_shadowMap, v_suv).r;\r\n" + //
				"      color.xyz *= clamp(dot(v_normal, lightDirection) + 0.45, 0.0, 1.0);\r\n" + //
				"      color.xyz *= 1.0 - shadow;\r\n" + //
				"      gl_FragColor = color;\r\n" + //
				"    }\r\n" + //
				"  ";
	}
}