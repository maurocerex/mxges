<%@ include file="/taglibs.jsp"%>
<s:form theme="simple" cssClass="pladform" id="FrecuenciaForm">
	<fieldset><legend> D&iacute;a </legend>
	<table width="100%">

		<tr>
			<td>
			<div align="center">
			<div class="errordiv" id="div.dia" style="color: #FF0000"></div>
			</div>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td valign="top">

			<table width="100%">
				<tr>
					<td><input type="checkbox" name="MON" onclick="habilitaDia(this)" >Lunes</input></td>
					<td><select name="MONhh" disabled="true">
						<option value="0">00 hrs</option>
						<option value="1">01 hrs</option>
						<option value="2">02 hrs</option>
						<option value="3">03 hrs</option>
						<option value="4">04 hrs</option>
						<option value="5">05 hrs</option>
						<option value="6">06 hrs</option>
						<option value="7">07 hrs</option>
						<option value="8">08 hrs</option>
						<option value="9">09 hrs</option>
						<option value="10" selected="selected">10 hrs</option>
						<option value="11">11 hrs</option>
						<option value="12">12 hrs</option>
						<option value="13">13 hrs</option>
						<option value="14">14 hrs</option>
						<option value="15">15 hrs</option>
						<option value="16">16 hrs</option>
						<option value="17">17 hrs</option>
						<option value="18">18 hrs</option>
						<option value="19">19 hrs</option>
						<option value="20">20 hrs</option>
						<option value="21">21 hrs</option>
						<option value="22">22 hrs</option>
						<option value="23">23 hrs</option>
					</select></td>
					<td><select name="MONmm" disabled="true">
						<option value="0">00 min</option>
						<option value="1">01 min</option>
						<option value="2">02 min</option>
						<option value="3">03 min</option>
						<option value="4">04 min</option>
						<option value="5">05 min</option>
						<option value="6">06 min</option>
						<option value="7">07 min</option>
						<option value="8">08 min</option>
						<option value="9">09 min</option>
						<option value="10">10 min</option>
						<option value="11">11 min</option>
						<option value="12">12 min</option>
						<option value="13">13 min</option>
						<option value="14">14 min</option>
						<option value="15">15 min</option>
						<option value="16">16 min</option>
						<option value="17">17 min</option>
						<option value="18">18 min</option>
						<option value="19">19 min</option>
						<option value="20">20 min</option>
						<option value="21">21 min</option>
						<option value="22">22 min</option>
						<option value="23">23 min</option>
						<option value="24">24 min</option>
						<option value="25">25 min</option>
						<option value="26">26 min</option>
						<option value="27">27 min</option>
						<option value="28">28 min</option>
						<option value="29">29 min</option>
						<option value="30">30 min</option>
						<option value="31">31 min</option>
						<option value="32">32 min</option>
						<option value="33">33 min</option>
						<option value="34">34 min</option>
						<option value="35">35 min</option>
						<option value="36">36 min</option>
						<option value="37">37 min</option>
						<option value="38">38 min</option>
						<option value="39">39 min</option>
						<option value="40">40 min</option>
						<option value="41">41 min</option>
						<option value="42">42 min</option>
						<option value="43">43 min</option>
						<option value="44">44 min</option>
						<option value="45">45 min</option>
						<option value="46">46 min</option>
						<option value="47">47 min</option>
						<option value="48">48 min</option>
						<option value="49">49 min</option>
						<option value="50">50 min</option>
						<option value="51">51 min</option>
						<option value="52">52 min</option>
						<option value="53">53 min</option>
						<option value="54">54 min</option>
						<option value="55">55 min</option>
						<option value="56">56 min</option>
						<option value="57">57 min</option>
						<option value="58">58 min</option>
						<option value="59">59 min</option>

					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="TUE" value="tue" onclick="habilitaDia(this)" >Martes </input></td>
					<td><select name="TUEhh" disabled="true">
						<option value="0">00 hrs</option>
						<option value="1">01 hrs</option>
						<option value="2">02 hrs</option>
						<option value="3">03 hrs</option>
						<option value="4">04 hrs</option>
						<option value="5">05 hrs</option>
						<option value="6">06 hrs</option>
						<option value="7">07 hrs</option>
						<option value="8">08 hrs</option>
						<option value="9">09 hrs</option>
						<option value="10" selected="selected">10 hrs</option>
						<option value="11">11 hrs</option>
						<option value="12">12 hrs</option>
						<option value="13">13 hrs</option>
						<option value="14">14 hrs</option>
						<option value="15">15 hrs</option>
						<option value="16">16 hrs</option>
						<option value="17">17 hrs</option>
						<option value="18">18 hrs</option>
						<option value="19">19 hrs</option>
						<option value="20">20 hrs</option>
						<option value="21">21 hrs</option>
						<option value="22">22 hrs</option>
						<option value="23">23 hrs</option>
					</select></td>
					<td><select name="TUEmm" disabled="true">
						<option value="0">00 min</option>
						<option value="1">01 min</option>
						<option value="2">02 min</option>
						<option value="3">03 min</option>
						<option value="4">04 min</option>
						<option value="5">05 min</option>
						<option value="6">06 min</option>
						<option value="7">07 min</option>
						<option value="8">08 min</option>
						<option value="9">09 min</option>
						<option value="10">10 min</option>
						<option value="11">11 min</option>
						<option value="12">12 min</option>
						<option value="13">13 min</option>
						<option value="14">14 min</option>
						<option value="15">15 min</option>
						<option value="16">16 min</option>
						<option value="17">17 min</option>
						<option value="18">18 min</option>
						<option value="19">19 min</option>
						<option value="20">20 min</option>
						<option value="21">21 min</option>
						<option value="22">22 min</option>
						<option value="23">23 min</option>
						<option value="24">24 min</option>
						<option value="25">25 min</option>
						<option value="26">26 min</option>
						<option value="27">27 min</option>
						<option value="28">28 min</option>
						<option value="29">29 min</option>
						<option value="30">30 min</option>
						<option value="31">31 min</option>
						<option value="32">32 min</option>
						<option value="33">33 min</option>
						<option value="34">34 min</option>
						<option value="35">35 min</option>
						<option value="36">36 min</option>
						<option value="37">37 min</option>
						<option value="38">38 min</option>
						<option value="39">39 min</option>
						<option value="40">40 min</option>
						<option value="41">41 min</option>
						<option value="42">42 min</option>
						<option value="43">43 min</option>
						<option value="44">44 min</option>
						<option value="45">45 min</option>
						<option value="46">46 min</option>
						<option value="47">47 min</option>
						<option value="48">48 min</option>
						<option value="49">49 min</option>
						<option value="50">50 min</option>
						<option value="51">51 min</option>
						<option value="52">52 min</option>
						<option value="53">53 min</option>
						<option value="54">54 min</option>
						<option value="55">55 min</option>
						<option value="56">56 min</option>
						<option value="57">57 min</option>
						<option value="58">58 min</option>
						<option value="59">59 min</option>
					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="WED" value="wed" onclick="habilitaDia(this)" >Mi&eacute;rcoles </input></td>
					<td><select name="WEDhh" disabled="true">
						<option value="0">00 hrs</option>
						<option value="1">01 hrs</option>
						<option value="2">02 hrs</option>
						<option value="3">03 hrs</option>
						<option value="4">04 hrs</option>
						<option value="5">05 hrs</option>
						<option value="6">06 hrs</option>
						<option value="7">07 hrs</option>
						<option value="8">08 hrs</option>
						<option value="9">09 hrs</option>
						<option value="10" selected="selected">10 hrs</option>
						<option value="11">11 hrs</option>
						<option value="12">12 hrs</option>
						<option value="13">13 hrs</option>
						<option value="14">14 hrs</option>
						<option value="15">15 hrs</option>
						<option value="16">16 hrs</option>
						<option value="17">17 hrs</option>
						<option value="18">18 hrs</option>
						<option value="19">19 hrs</option>
						<option value="20">20 hrs</option>
						<option value="21">21 hrs</option>
						<option value="22">22 hrs</option>
						<option value="23">23 hrs</option>
					</select></td>
					<td><select name="WEDmm" disabled="true">
						<option value="0">00 min</option>
						<option value="1">01 min</option>
						<option value="2">02 min</option>
						<option value="3">03 min</option>
						<option value="4">04 min</option>
						<option value="5">05 min</option>
						<option value="6">06 min</option>
						<option value="7">07 min</option>
						<option value="8">08 min</option>
						<option value="9">09 min</option>
						<option value="10">10 min</option>
						<option value="11">11 min</option>
						<option value="12">12 min</option>
						<option value="13">13 min</option>
						<option value="14">14 min</option>
						<option value="15">15 min</option>
						<option value="16">16 min</option>
						<option value="17">17 min</option>
						<option value="18">18 min</option>
						<option value="19">19 min</option>
						<option value="20">20 min</option>
						<option value="21">21 min</option>
						<option value="22">22 min</option>
						<option value="23">23 min</option>
						<option value="24">24 min</option>
						<option value="25">25 min</option>
						<option value="26">26 min</option>
						<option value="27">27 min</option>
						<option value="28">28 min</option>
						<option value="29">29 min</option>
						<option value="30">30 min</option>
						<option value="31">31 min</option>
						<option value="32">32 min</option>
						<option value="33">33 min</option>
						<option value="34">34 min</option>
						<option value="35">35 min</option>
						<option value="36">36 min</option>
						<option value="37">37 min</option>
						<option value="38">38 min</option>
						<option value="39">39 min</option>
						<option value="40">40 min</option>
						<option value="41">41 min</option>
						<option value="42">42 min</option>
						<option value="43">43 min</option>
						<option value="44">44 min</option>
						<option value="45">45 min</option>
						<option value="46">46 min</option>
						<option value="47">47 min</option>
						<option value="48">48 min</option>
						<option value="49">49 min</option>
						<option value="50">50 min</option>
						<option value="51">51 min</option>
						<option value="52">52 min</option>
						<option value="53">53 min</option>
						<option value="54">54 min</option>
						<option value="55">55 min</option>
						<option value="56">56 min</option>
						<option value="57">57 min</option>
						<option value="58">58 min</option>
						<option value="59">59 min</option>


					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="THU" value="thu" onclick="habilitaDia(this)" >Jueves </input></td>
					<td><select name="THUhh" disabled="true">
						<option value="0">00 hrs</option>
						<option value="1">01 hrs</option>
						<option value="2">02 hrs</option>
						<option value="3">03 hrs</option>
						<option value="4">04 hrs</option>
						<option value="5">05 hrs</option>
						<option value="6">06 hrs</option>
						<option value="7">07 hrs</option>
						<option value="8">08 hrs</option>
						<option value="9">09 hrs</option>
						<option value="10" selected="selected">10 hrs</option>
						<option value="11">11 hrs</option>
						<option value="12">12 hrs</option>
						<option value="13">13 hrs</option>
						<option value="14">14 hrs</option>
						<option value="15">15 hrs</option>
						<option value="16">16 hrs</option>
						<option value="17">17 hrs</option>
						<option value="18">18 hrs</option>
						<option value="19">19 hrs</option>
						<option value="20">20 hrs</option>
						<option value="21">21 hrs</option>
						<option value="22">22 hrs</option>
						<option value="23">23 hrs</option>
					</select></td>
					<td><select name="THUmm" disabled="true">
						<option value="0">00 min</option>
						<option value="1">01 min</option>
						<option value="2">02 min</option>
						<option value="3">03 min</option>
						<option value="4">04 min</option>
						<option value="5">05 min</option>
						<option value="6">06 min</option>
						<option value="7">07 min</option>
						<option value="8">08 min</option>
						<option value="9">09 min</option>
						<option value="10">10 min</option>
						<option value="11">11 min</option>
						<option value="12">12 min</option>
						<option value="13">13 min</option>
						<option value="14">14 min</option>
						<option value="15">15 min</option>
						<option value="16">16 min</option>
						<option value="17">17 min</option>
						<option value="18">18 min</option>
						<option value="19">19 min</option>
						<option value="20">20 min</option>
						<option value="21">21 min</option>
						<option value="22">22 min</option>
						<option value="23">23 min</option>
						<option value="24">24 min</option>
						<option value="25">25 min</option>
						<option value="26">26 min</option>
						<option value="27">27 min</option>
						<option value="28">28 min</option>
						<option value="29">29 min</option>
						<option value="30">30 min</option>
						<option value="31">31 min</option>
						<option value="32">32 min</option>
						<option value="33">33 min</option>
						<option value="34">34 min</option>
						<option value="35">35 min</option>
						<option value="36">36 min</option>
						<option value="37">37 min</option>
						<option value="38">38 min</option>
						<option value="39">39 min</option>
						<option value="40">40 min</option>
						<option value="41">41 min</option>
						<option value="42">42 min</option>
						<option value="43">43 min</option>
						<option value="44">44 min</option>
						<option value="45">45 min</option>
						<option value="46">46 min</option>
						<option value="47">47 min</option>
						<option value="48">48 min</option>
						<option value="49">49 min</option>
						<option value="50">50 min</option>
						<option value="51">51 min</option>
						<option value="52">52 min</option>
						<option value="53">53 min</option>
						<option value="54">54 min</option>
						<option value="55">55 min</option>
						<option value="56">56 min</option>
						<option value="57">57 min</option>
						<option value="58">58 min</option>
						<option value="59">59 min</option>


					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="FRI" value="fri" onclick="habilitaDia(this)" >Viernes </input></td>
					<td><select name="FRIhh" disabled="true">
						<option value="0">00 hrs</option>
						<option value="1">01 hrs</option>
						<option value="2">02 hrs</option>
						<option value="3">03 hrs</option>
						<option value="4">04 hrs</option>
						<option value="5">05 hrs</option>
						<option value="6">06 hrs</option>
						<option value="7">07 hrs</option>
						<option value="8">08 hrs</option>
						<option value="9">09 hrs</option>
						<option value="10" selected="selected">10 hrs</option>
						<option value="11">11 hrs</option>
						<option value="12">12 hrs</option>
						<option value="13">13 hrs</option>
						<option value="14">14 hrs</option>
						<option value="15">15 hrs</option>
						<option value="16">16 hrs</option>
						<option value="17">17 hrs</option>
						<option value="18">18 hrs</option>
						<option value="19">19 hrs</option>
						<option value="20">20 hrs</option>
						<option value="21">21 hrs</option>
						<option value="22">22 hrs</option>
						<option value="23">23 hrs</option>
					</select></td>
					<td><select name="FRImm" disabled="true">
						<option value="0">00 min</option>
						<option value="1">01 min</option>
						<option value="2">02 min</option>
						<option value="3">03 min</option>
						<option value="4">04 min</option>
						<option value="5">05 min</option>
						<option value="6">06 min</option>
						<option value="7">07 min</option>
						<option value="8">08 min</option>
						<option value="9">09 min</option>
						<option value="10">10 min</option>
						<option value="11">11 min</option>
						<option value="12">12 min</option>
						<option value="13">13 min</option>
						<option value="14">14 min</option>
						<option value="15">15 min</option>
						<option value="16">16 min</option>
						<option value="17">17 min</option>
						<option value="18">18 min</option>
						<option value="19">19 min</option>
						<option value="20">20 min</option>
						<option value="21">21 min</option>
						<option value="22">22 min</option>
						<option value="23">23 min</option>
						<option value="24">24 min</option>
						<option value="25">25 min</option>
						<option value="26">26 min</option>
						<option value="27">27 min</option>
						<option value="28">28 min</option>
						<option value="29">29 min</option>
						<option value="30">30 min</option>
						<option value="31">31 min</option>
						<option value="32">32 min</option>
						<option value="33">33 min</option>
						<option value="34">34 min</option>
						<option value="35">35 min</option>
						<option value="36">36 min</option>
						<option value="37">37 min</option>
						<option value="38">38 min</option>
						<option value="39">39 min</option>
						<option value="40">40 min</option>
						<option value="41">41 min</option>
						<option value="42">42 min</option>
						<option value="43">43 min</option>
						<option value="44">44 min</option>
						<option value="45">45 min</option>
						<option value="46">46 min</option>
						<option value="47">47 min</option>
						<option value="48">48 min</option>
						<option value="49">49 min</option>
						<option value="50">50 min</option>
						<option value="51">51 min</option>
						<option value="52">52 min</option>
						<option value="53">53 min</option>
						<option value="54">54 min</option>
						<option value="55">55 min</option>
						<option value="56">56 min</option>
						<option value="57">57 min</option>
						<option value="58">58 min</option>
						<option value="59">59 min</option>


					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="SAT" value="sat" onclick="habilitaDia(this)" >S&aacute;bado </input></td>
					<td><select name="SAThh" disabled="true">
						<option value="0">00 hrs</option>
						<option value="1">01 hrs</option>
						<option value="2">02 hrs</option>
						<option value="3">03 hrs</option>
						<option value="4">04 hrs</option>
						<option value="5">05 hrs</option>
						<option value="6">06 hrs</option>
						<option value="7">07 hrs</option>
						<option value="8">08 hrs</option>
						<option value="9">09 hrs</option>
						<option value="10" selected="selected">10 hrs</option>
						<option value="11">11 hrs</option>
						<option value="12">12 hrs</option>
						<option value="13">13 hrs</option>
						<option value="14">14 hrs</option>
						<option value="15">15 hrs</option>
						<option value="16">16 hrs</option>
						<option value="17">17 hrs</option>
						<option value="18">18 hrs</option>
						<option value="19">19 hrs</option>
						<option value="20">20 hrs</option>
						<option value="21">21 hrs</option>
						<option value="22">22 hrs</option>
						<option value="23">23 hrs</option>
					</select></td>
					<td><select name="SATmm" disabled="true">
						<option value="0">00 min</option>
						<option value="1">01 min</option>
						<option value="2">02 min</option>
						<option value="3">03 min</option>
						<option value="4">04 min</option>
						<option value="5">05 min</option>
						<option value="6">06 min</option>
						<option value="7">07 min</option>
						<option value="8">08 min</option>
						<option value="9">09 min</option>
						<option value="10">10 min</option>
						<option value="11">11 min</option>
						<option value="12">12 min</option>
						<option value="13">13 min</option>
						<option value="14">14 min</option>
						<option value="15">15 min</option>
						<option value="16">16 min</option>
						<option value="17">17 min</option>
						<option value="18">18 min</option>
						<option value="19">19 min</option>
						<option value="20">20 min</option>
						<option value="21">21 min</option>
						<option value="22">22 min</option>
						<option value="23">23 min</option>
						<option value="24">24 min</option>
						<option value="25">25 min</option>
						<option value="26">26 min</option>
						<option value="27">27 min</option>
						<option value="28">28 min</option>
						<option value="29">29 min</option>
						<option value="30">30 min</option>
						<option value="31">31 min</option>
						<option value="32">32 min</option>
						<option value="33">33 min</option>
						<option value="34">34 min</option>
						<option value="35">35 min</option>
						<option value="36">36 min</option>
						<option value="37">37 min</option>
						<option value="38">38 min</option>
						<option value="39">39 min</option>
						<option value="40">40 min</option>
						<option value="41">41 min</option>
						<option value="42">42 min</option>
						<option value="43">43 min</option>
						<option value="44">44 min</option>
						<option value="45">45 min</option>
						<option value="46">46 min</option>
						<option value="47">47 min</option>
						<option value="48">48 min</option>
						<option value="49">49 min</option>
						<option value="50">50 min</option>
						<option value="51">51 min</option>
						<option value="52">52 min</option>
						<option value="53">53 min</option>
						<option value="54">54 min</option>
						<option value="55">55 min</option>
						<option value="56">56 min</option>
						<option value="57">57 min</option>
						<option value="58">58 min</option>
						<option value="59">59 min</option>


					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><input type="checkbox" name="SUN" value="sun" onclick="habilitaDia(this)" >Domingo </input></td>
					<td><select name="SUNhh" disabled="true">
						<option value="0">00 hrs</option>
						<option value="1">01 hrs</option>
						<option value="2">02 hrs</option>
						<option value="3">03 hrs</option>
						<option value="4">04 hrs</option>
						<option value="5">05 hrs</option>
						<option value="6">06 hrs</option>
						<option value="7">07 hrs</option>
						<option value="8">08 hrs</option>
						<option value="9">09 hrs</option>
						<option value="10" selected="selected">10 hrs</option>
						<option value="11">11 hrs</option>
						<option value="12">12 hrs</option>
						<option value="13">13 hrs</option>
						<option value="14">14 hrs</option>
						<option value="15">15 hrs</option>
						<option value="16">16 hrs</option>
						<option value="17">17 hrs</option>
						<option value="18">18 hrs</option>
						<option value="19">19 hrs</option>
						<option value="20">20 hrs</option>
						<option value="21">21 hrs</option>
						<option value="22">22 hrs</option>
						<option value="23">23 hrs</option>
					</select></td>
					<td><select name="SUNmm" disabled="true">
						<option value="0">00 min</option>
						<option value="1">01 min</option>
						<option value="2">02 min</option>
						<option value="3">03 min</option>
						<option value="4">04 min</option>
						<option value="5">05 min</option>
						<option value="6">06 min</option>
						<option value="7">07 min</option>
						<option value="8">08 min</option>
						<option value="9">09 min</option>
						<option value="10">10 min</option>
						<option value="11">11 min</option>
						<option value="12">12 min</option>
						<option value="13">13 min</option>
						<option value="14">14 min</option>
						<option value="15">15 min</option>
						<option value="16">16 min</option>
						<option value="17">17 min</option>
						<option value="18">18 min</option>
						<option value="19">19 min</option>
						<option value="20">20 min</option>
						<option value="21">21 min</option>
						<option value="22">22 min</option>
						<option value="23">23 min</option>
						<option value="24">24 min</option>
						<option value="25">25 min</option>
						<option value="26">26 min</option>
						<option value="27">27 min</option>
						<option value="28">28 min</option>
						<option value="29">29 min</option>
						<option value="30">30 min</option>
						<option value="31">31 min</option>
						<option value="32">32 min</option>
						<option value="33">33 min</option>
						<option value="34">34 min</option>
						<option value="35">35 min</option>
						<option value="36">36 min</option>
						<option value="37">37 min</option>
						<option value="38">38 min</option>
						<option value="39">39 min</option>
						<option value="40">40 min</option>
						<option value="41">41 min</option>
						<option value="42">42 min</option>
						<option value="43">43 min</option>
						<option value="44">44 min</option>
						<option value="45">45 min</option>
						<option value="46">46 min</option>
						<option value="47">47 min</option>
						<option value="48">48 min</option>
						<option value="49">49 min</option>
						<option value="50">50 min</option>
						<option value="51">51 min</option>
						<option value="52">52 min</option>
						<option value="53">53 min</option>
						<option value="54">54 min</option>
						<option value="55">55 min</option>
						<option value="56">56 min</option>
						<option value="57">57 min</option>
						<option value="58">58 min</option>
						<option value="59">59 min</option>


					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>

			</td>
		</tr>
	</table>
	</fieldset>
</s:form>