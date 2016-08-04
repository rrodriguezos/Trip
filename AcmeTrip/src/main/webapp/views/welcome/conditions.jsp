<%--
 * list.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}

$(document).ready(function() {
	$("#english").hide();
	$("#spanish").hide();
    var language=getCookie("language");
    if (language=="" || language=="en") {
    	$("#english").show();
    }else{
    	$("#spanish").show();
    }
});
</script>

<div id="english"> 
 
<h2>OBJECT</h2>
These Terms of Use, Privacy Policy and Sale (hereinafter "General Conditions" the) govern the use of the website www.acme.com (hereinafter the Website) Acme Trip, S.L. (Hereinafter "Acme Trip"), available to people who access its Web site in order to provide them information about products and services, own and / or third party partners, and provide access to, and the procurement of services and goods through the same (all together called the "services").
<br/>
Acme Trip, established in John Doe, 2 D.102.3 28923 Alcorcón (Madrid) is a Spanish limited liability company that owns this Web site whose use is regulated by this document, with CIF number B-85844033 and entered in the Register Mercantil de Madrid, Volume 27335, Page M-492541, Folio 89, Section 8, first registration. To contact Acme Trip, you can use the e-mail above, as well as the email address contacto@acme.com.
By the very nature of the Web Site and its content and purpose, the entire navigation practice can be carried out by the same must be enjoying the status of Customer, which is acquired according to the procedures in the same. Therefore, the above condition Customer supposed adherence to the General Conditions in the version published at the time that you access the Web Site. Acme Trip reserves the right to modify at any time the presentation and configuration of the Website, and the present General Conditions. Therefore, Acme Trip endorses you read it carefully each time you access the Web Site.
<br/>
In any case, there are pages accessible to the general public website, with respect to which Acme Trip also want to fulfill their legal obligations and regulate the use thereof. In this way, users who access these parts of the Website agree to be bound by the act of visiting such sites or by the terms and conditions contained in these Terms, to the extent it may be applicable to them.
<br/>
Finally, by the very nature of this Website, you may be modified or include changes in the content of these General Conditions. Therefore, the client and others who do not enjoy this condition, are required to agree to this Terms each time you access the Web site, assuming that they will apply the relevant conditions that are in force at the time their access.
<br/><br/>
<h2>ACCESS AND SECURITY</h2>

Access to the Services requires prior registration of users once accept the General Conditions, becoming regarded as customers.
<br/>
Customer ID will consist of your email address and password. To access the Customer own, you will need to include this identifier and a password must contain at least 4 characters.
<br/>
The use of the password is not transferable, the transfer, even temporarily, to third parties not being allowed. In this regard, the Customer agrees to make diligent and keep it secret, assuming full responsibility for the consequences of disclosure to third parties.
<br/>
In the event that the customer knows or suspects the use of your password by third parties, you must change it immediately, in the way it is collected on the Website.
<br/><br/>
<h2>CORRECT USE OF SERVICES</h2>
Customer agrees to use the Services in a diligent, correct and lawful, and in particular, by way of example and not limitation, you agree to refrain from:
<br/>
use the Services in a manner, or for purposes contrary to law, morality and generally accepted good customs or public order;
reproducing, copying, distributing, allowing public access through any means of public communication, transforming or modifying the Services, unless you have permission from the owner of the corresponding rights or it is legally permitted;
any action that could be considered a violation of any rights of intellectual property belonging to Acme Trip or third parties;
use the Services and, in particular, information of any kind obtained through the Website to send advertising, communications for direct marketing purposes or any other commercial purpose, unsolicited messages addressed to a number of people regardless of its purpose, and to sell or otherwise disclose such information;
Customer liable for damages of any kind that Acme Trip may suffer during or as a result of a breach of any of the obligations outlined above and any others included in the General Conditions and / or imposed by law in connection use of the web Site.
<br/>
Acme Trip shall at all times respect the law in force, and shall be entitled to terminate, in its sole discretion, the Customer Service or exclude Web Site for alleged commission, complete or incomplete, of any of the crimes or offenses typified by the current, or in case of observing any conduct which it considers Acme Trip are contrary to these Terms and Conditions, the Terms of operating this website, the Law, the rules established by Acme Trip or its collaborators or Penal Code they can disrupt the operation, image, credibility and / or prestige Acme Trip or colleagues.
<br/><br/>
<h2>PROPERTY RIGHTS</h2>
All contents of the Web Site, such as text, graphics, photographs, logos, icons, images, as well as graphic design, source code and software, are the exclusive property of Acme Trip or third parties, whose rights legitimately holds about Acme Trip , and are therefore protected by national and international legislation.
<br/>
the use of all elements of intellectual property and commercial distribution, modification or alteration is strictly prohibited.
<br/>
Violation of any of these rights may constitute a violation of these provisions, as well as an offense punishable under Articles 270 and following of the Penal Code.
<br/>
Customers who send to Web Site observations, opinions or comments via e-mail service or by any other means, in cases where the nature of the services where possible, is understood to authorize Acme Trip for reproduction distribution, public communication, transformation, and the exercise of any right of exploitation, of such observations, opinions or comments, during all the time to protect copyright legally in force with no territorial restrictions. It is also understood that this authorization is granted for free.
<br/>
The claims filed by clients regarding possible breaches of the rights of intellectual property on any of the Services of this Website should be addressed to the following email address: contacto@acme.com.
<br/><br/>
<h2>EXCLUSION OF WARRANTIES AND LIABILITY</h2>
Regardless of the provisions of the General Terms and Conditions relating to the procurement of goods contained in this Web Site, Acme Trip not responsible for the truthfulness, accuracy and quality of this Web Site, its services, information and materials. These services, information and materials are provided "as is" and are accessible without warranty of any kind.
<br/>
Acme Trip reserves the right to terminate your access to the Website, and the provision of any or all Services provided through it at any time and without notice, either for technical reasons, security, control , maintenance of power failure or any other cause.
<br/>
Consequently, Acme Trip not guarantee the reliability, availability or continuity of its Web Site or the Services, so the use thereof by the Client is carried out by its own risk, without, in no time Acme Trip be held accountable in this regard.
<br/>
Acme Trip not be liable in the event of interruptions of the Services, delays, errors, malfunctions and, in general, any other problems that originate from causes beyond the control of Acme Trip, and / or due to willful misconduct Customer or negligent and / or caused by fortuitous events or force majeure. Notwithstanding the provisions of Article 1105 of the Civil Code, shall be construed as included in the concept of force majeure, in addition, for the purposes of these General Conditions, all those events beyond the control of Acme Trip, such as failure of third parties , operators or service companies, acts of government, lack of access to third party networks, acts or omissions by public authorities, those produced as a result of natural phenomena, blackouts, etc. and attacks by hackers or others specialized in security or integrity of the system, provided that Acme Trip adopted reasonable security measures in accordance with the prior art. In any case, whatever its cause, Acme Trip not be liable either for direct or indirect damages, consequential damages and / or loss of profits.
<br/>
Acme Trip excludes all liability for damages of any kind that may result from the lack of truthfulness, accuracy, completeness and / or timeliness of the services transmitted, distributed, stored, made available or received, obtained or is accessed through the web site as well as the services provided or offered by third parties or entities. Acme Trip try as much as possible to update and correct all information hosted on its Web site that does not meet the minimum guarantee of truthfulness. However, it is exempted from liability for its failure to update or rectification as well as the contents and information in the same discharges. In this sense, Acme Trip has no obligation and does not control the content transmitted, distributed or made available to third parties by customers or employees, except in cases where it is required by law or when required by a judicial authority or competent administrative.
<br/>
Similarly, Acme Trip excludes all liability for damages of any kind that may result from the presence of viruses or the presence of other harmful elements in the contents that may cause alterations in the computer systems as well as documents or systems stored therein.
<br/>
Acme Trip not responsible for the Customer to use the Services of the Website or your passwords, and any other material thereof, may infringe the intellectual property or other rights of third parties.
<br/>
Customer agrees to indemnify Acme Trip for any loss, damage, sanction, expense (including, without limitation, attorneys' fees) or civil, administrative or any other liability, which may suffer Acme Trip relevant to the breach or partial or defective by you of the provisions of these Terms and conditions or applicable law compliance, and in particular, in relation to its obligations concerning protection of personal data contained in these conditions or established in the LOPD and implementing regulations.
<br/><br/>
<h2>LINKS TO OTHER WEB SITES</h2>
Acme Trip not warrant or assume any responsibility for damages suffered by third parties access to services through connections or links of the linked sites or the accuracy or reliability thereof. The purpose of the links that appear in Acme Trip is exclusively to inform the customer about the existence of other information sources on the Internet where you can expand the services offered by the Portal. Acme Trip shall in no way responsible for the results obtained through these links or the consequences arising from access by clients to them. These services are provided by these third parties, so Acme Trip can not and does not control the legality of the Services or their quality. Consequently, the Customer must exercise caution in evaluating and using information and services on third party content.
<br/><br/>
<h2>APPLICABLE LAW AND JURISDICTION</h2>
For any interpretative or contentious issues that may arise will apply Spanish law and any dispute, both parties agree to submit, renouncing any other jurisdiction that may correspond to the jurisdiction of the Courts of the city of Madrid.

</div>

<div id="spanish">
<h2>OBJETO</h2>
Las presentes Condiciones Generales de Uso, Política de Privacidad y Venta (en adelante, las "Condiciones Generales") regulan el uso del sitio web www.acme.com (en adelante, el Sitio Web) que Taiwan Trade Investment, S.L. (en adelante "Acme Trip"), pone a disposición de las personas que accedan a su Sitio Web con el fin de proporcionales información sobre productos y servicios, propios y/o de terceros colaboradores, y facilitarles el acceso a los mismos, así como la contratación de servicios y bienes por medio de la misma (todo ello denominado conjuntamente los "Servicios").
<br/>
Acme Trip, con domicilio social en Calle John Doe, 2 D.102.3 28923 Alcorcón (Madrid) , es una sociedad de responsabilidad limitada española titular del presente Sitio Web cuya utilización se regula mediante este documento, con CIF número B-85844033 e inscrita en el Registro Mercantil de Madrid al Tomo 27.335, Hoja M-492.541, Folio 89, Sección 8, inscripción primera. Para contactar con Acme Trip, puede utilizar la dirección de correo postal arriba indicada, así como la dirección de correo electrónica contacto@acme.com.
Por la propia naturaleza del Sitio Web, así como de su contenido y finalidad, la práctica totalidad de la navegación que se puede llevar a cabo por el mismo ha de hacerse gozando de la condición de Cliente, la cual se adquiere según los procedimientos recogidos en la misma. Por lo tanto, la citada condición de Cliente supone la adhesión a las Condiciones Generales en la versión publicada en el momento en que se acceda al Sitio Web. Acme Trip se reserva el derecho de modificar, en cualquier momento, la presentación y configuración del Sitio Web, así como las presentes Condiciones Generales. Por ello, Acme Trip recomienda al Cliente leer el mismo atentamente cada vez que acceda al Sitio Web.
<br/>
En cualquier caso, existen páginas del Sitio Web accesibles al público en general, respecto a las cuales Acme Trip también desea cumplir con sus obligaciones legales, así como regular el uso de las mismas. En este sentido, los usuarios que accedan a estas partes del Sitio Web aceptan quedar sometidos, por el hecho de acceder a las citadas páginas, por los términos y condiciones recogidos en estas Condiciones Generales, en la medida que ello les pueda ser de aplicación.
<br/>
Por último, por la naturaleza propia del presente Sitio Web, es posible que se modifiquen o incluyan cambios en el contenido de las presentes Condiciones Generales. Por esto, el Cliente, así como otros usuarios que no gocen de esta condición, quedan obligados a acceder a las presente Condiciones Generales cada vez que accedan al Sitio Web, asumiendo que les serán de aplicación las condiciones correspondientes que se encuentren vigentes en el momento de su acceso.
<br/><br/>
<h2>ACCESO Y SEGURIDAD</h2>

El acceso a los Servicios requiere el registro previo de los usuarios, una vez acepten las Condiciones Generales, pasando a ser considerados como Clientes .
<br/>
El identificador del Cliente estará compuesto por su dirección de correo electrónico y una contraseña. Para el acceso a la cuenta propia del Cliente, será necesario la inclusión de este identificador, así como de una contraseña que deberá contener como mínimo 4 caracteres.
<br/>
El uso de la contraseña es personal e intransferible, no estando permitida la cesión, ni siquiera temporal, a terceros. En tal sentido, el Cliente se compromete a hacer un uso diligente y a mantener en secreto la misma, asumiendo toda responsabilidad por las consecuencias de su divulgación a terceros.
<br/>
En el supuesto de que el Cliente conozca o sospeche del uso de su contraseña por terceros, deberá modificar la misma de forma inmediata, en el modo en que se recoge en el Sitio Web.
<br/><br/>
<h2>UTILIZACIÓN CORRECTA DE LOS SERVICIOS</h2>
El Cliente se obliga a usar los Servicios de forma diligente, correcta y lícita y, en particular, a título meramente enunciativo y no limitativo, se compromete a abstenerse de:
<br/>
utilizar los Servicios de forma, con fines o efectos contrarios a la ley, a la moral y a las buenas costumbres generalmente aceptadas o al orden público;
reproducir o copiar, distribuir, permitir el acceso del público a través de cualquier modalidad de comunicación pública, transformar o modificar los Servicios, a menos que se cuente con la autorización del titular de los correspondientes derechos o ello resulte legalmente permitido;
realizar cualquier acto que pueda ser considerado una vulneración de cualesquiera derechos de propiedad intelectual o industrial pertenecientes a Acme Trip o a terceros;
emplear los Servicios y, en particular, la información de cualquier clase obtenida a través del Sitio Web para remitir publicidad, comunicaciones con fines de venta directa o con cualquier otra clase de finalidad comercial, mensajes no solicitados dirigidos a una pluralidad de personas con independencia de su finalidad, así como de comercializar o divulgar de cualquier modo dicha información;
El Cliente responderá de los daños y perjuicios de toda naturaleza que Acme Trip pueda sufrir, con ocasión o como consecuencia del incumplimiento de cualquiera de las obligaciones anteriormente expuestas así como cualesquiera otras incluidas en las presentes Condiciones Generales y/o las impuestas por la Ley en relación con la utilización del Sitio Web .
<br/>
Acme Trip velará en todo momento por el respeto del ordenamiento jurídico vigente, y estará legitimada para interrumpir, a su entera discreción, el Servicio o excluir al Cliente del Sitio Web en caso de presunta comisión, completa o incompleta, de alguno de los delitos o faltas tipificados por el Código Penal vigente, o en caso de observar cualesquiera conductas que a juicio de Acme Trip resulten contrarias a estas Condiciones Generales, las Condiciones Generales de Contratación que operan para este Sitio Web, la Ley, las normas establecidas por Acme Trip o sus colaboradores o puedan perturbar el buen funcionamiento, imagen, credibilidad y/o prestigio de Acme Trip o sus colaboradores.
<br/><br/>
<h2>DERECHOS DE PROPIEDAD</h2>
Todos los contenidos del Sitio Web, tales como textos, gráficos, fotografías, logotipos, iconos, imágenes, así como el diseño gráfico, código fuente y software, son de la exclusiva propiedad de Acme Trip o de terceros, cuyos derechos al respecto ostenta legítimamente Acme Trip, estando por lo tanto protegidos por la legislación nacional e internacional.
<br/>
Queda estrictamente prohibido la utilización de todos los elementos objeto de propiedad industrial e intelectual con fines comerciales así como su distribución, modificación, alteración o descompilación.
<br/>
La infracción de cualquiera de los citados derechos puede constituir una vulneración de las presentes disposiciones, así como un delito castigado de acuerdo con los artículos 270 y siguientes del Código Penal.
<br/>
Aquellos Clientes que envíen al Sitio Web observaciones, opiniones o comentarios por medio del servicio de correo electrónico o por cualquier otro medio, en los casos en los que por la naturaleza de los Servicios ello sea posible, se entiende que autorizan a Acme Trip para la reproducción, distribución, comunicación pública, transformación, y el ejercicio de cualquier otro derecho de explotación, de tales observaciones, opiniones o comentarios, por todo el tiempo de protección de derecho de autor que esté previsto legalmente y sin limitación territorial. Asimismo, se entiende que esta autorización se hace a título gratuito.
<br/>
Las reclamaciones que pudieran interponerse por los Clientes en relación con posibles incumplimientos de los derechos de propiedad intelectual o industrial sobre cualesquiera de los Servicios de este Sitio Web deberán dirigirse a la siguiente dirección de correo electrónico: contacto@acme.com.
<br/><br/>
<h2>EXCLUSIÓN DE GARANTÍAS Y DE RESPONSABILIDAD</h2>
Con independencia de lo establecido en las Condiciones Generales de Contratación relativas a la contratación de bienes recogidas en el presente Sitio Web, Acme Trip no se hace responsable de la veracidad, exactitud y calidad del presente Sitio Web, sus servicios, información y materiales. Dichos servicios, información y materiales son presentados "tal cual" y son accesibles sin garantías de ninguna clase.
<br/>
Acme Trip se reserva el derecho a interrumpir el acceso al Sitio Web, así como la prestación de cualquiera o de todos los Servicios que se prestan a través del mismo en cualquier momento y sin previo aviso, ya sea por motivos técnicos, de seguridad, de control, de mantenimiento, por fallos de suministro eléctrico o por cualquier otra causa justificada.
<br/>
En consecuencia, Acme Trip no garantiza la fiabilidad, la disponibilidad ni la continuidad de su Sitio Web ni de los Servicios, por lo que la utilización de los mismos por parte del Cliente se lleva a cabo por su propia cuenta y riesgo, sin que, en ningún momento, puedan exigirse responsabilidades a Acme Trip en este sentido.
<br/>
Acme Trip no será responsable en caso de que existan interrupciones de los Servicios, demoras, errores, mal funcionamiento del mismo y, en general, demás inconvenientes que tengan su origen en causas que escapan del control de Acme Trip, y/o debida a una actuación dolosa o culposa del Cliente y/o tenga por origen causas de caso fortuito o fuerza Mayor. Sin perjuicio de lo establecido en el artículo 1105 del Código Civil, se entenderán incluidos en el concepto de Fuerza Mayor, además, y a los efectos de las presentes Condiciones Generales, todos aquellos acontecimientos acaecidos fuera del control de Acme Trip, tales como: fallo de terceros, operadores o compañías de servicios, actos de Gobierno, falta de acceso a redes de terceros, actos u omisiones de las Autoridades Públicas, aquellos otros producidos como consecuencia de fenómenos naturales, apagones, etc y el ataque de hackers o terceros especializados en la seguridad o integridad del sistema informático, siempre que Acme Trip haya adoptado las medidas de seguridad razonables de acuerdo con el estado de la técnica. En cualquier caso, sea cual fuere su causa, Acme Trip no asumirá responsabilidad alguna ya sea por daños directos o indirectos, daño emergente y/o por lucro cesante.
<br/>
Acme Trip excluye cualquier responsabilidad por los daños y perjuicios de toda naturaleza que puedan deberse a la falta de veracidad, exactitud, exhaustividad y/o actualidad de los Servicios transmitidos, difundidos, almacenados, puestos a disposición o recibidos, obtenidos o a los que se haya accedido a través del Sitio Web así como por los Servicios prestados u ofertados por terceras personas o entidades. Acme Trip tratará en la medida de lo posible de actualizar y rectificar aquella información alojada en su Sitio Web que no cumpla con las mínimas garantías de veracidad. No obstante quedará exonerada de responsabilidad por su no actualización o rectificación así como por los contenidos e informaciones vertidos en la misma. En este sentido, Acme Trip no tiene obligación de controlar y no controla los contenidos transmitidos, difundidos o puestos a disposición de terceros por los Clientes o colaboradores, salvo los supuestos en que así lo exija la legislación vigente o cuando sea requerido por una Autoridad Judicial o Administrativa competente.
<br/>
De igual modo, Acme Trip excluye cualquier responsabilidad por los daños y perjuicios de toda clase que puedan deberse a la presencia de virus o a la presencia de otros elementos lesivos en los contenidos que puedan producir alteración en los sistemas informáticos así como en los documentos o sistemas almacenados en los mismos.
<br/>
Acme Trip no se hace responsable por la utilización que el Cliente realice de los Servicios del Sitio Web ni de sus contraseñas, así como de cualquier otro material del mismo, infringiendo los derechos de propiedad intelectual o industrial o cualquier otro derecho de terceros.
<br/>
El Cliente se obliga a mantener indemne a Acme Trip, por cualquier daño, perjuicio, sanción, gasto (incluyendo, sin limitación, honorarios de abogados) o responsabilidad civil, administrativa o de cualquier otra índole, que pudiera sufrir Acme Trip que guarde relación con el incumplimiento o cumplimiento parcial o defectuoso por su parte de lo establecido en las presentes Condiciones Generales o en la legislación aplicable, y, en especial, en relación con sus obligaciones relativas a protección de datos de carácter personal recogidas en las presentes condiciones o establecidas en la LOPD y normativa de desarrollo.
<br/><br/>
<h2>ENLACES A OTROS SITIOS WEB</h2>
Acme Trip no garantiza ni asume ningún tipo de responsabilidad por los daños y perjuicios sufridos por el acceso a Servicios de terceros a través de conexiones, vínculos o links de los sitios enlazados ni sobre la exactitud o fiabilidad de los mismos. La función de los enlaces que aparecen en Acme Trip es exclusivamente la de informar al Cliente sobre la existencia de otras fuentes de información en Internet, donde podrá ampliar los Servicios ofrecidos por el Portal. Acme Trip no será en ningún caso responsable del resultado obtenido a través de dichos enlaces o de las consecuencias que se deriven del acceso por los Clientes a los mismos. Estos Servicios de terceros son proporcionados por éstos, por lo que Acme Trip no puede controlar y no controla la licitud de los Servicios ni su calidad. En consecuencia, el Cliente debe extremar la prudencia en la valoración y utilización de la información y servicios existentes en los contenidos de terceros.
<br/><br/>
<h2>LEY APLICABLE Y JURISDICCIÓN</h2>
Para cuantas cuestiones interpretativas o litigiosas que pudieran plantearse será de aplicación la legislación española y en caso de controversia, ambas partes acuerdan someterse, con renuncia a cualquier otro fuero que pudiera corresponderle, a la jurisdicción de los Juzgados y Tribunales de la ciudad de Madrid.

</div>