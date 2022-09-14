package a2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class A2016Day11 extends A2016 {
	public A2016Day11(int day) {
		super(day);
	}

	public static void main(String[] args0) {
		A2016Day11 d = new A2016Day11(11);
		long startTime = System.currentTimeMillis();
		System.out.println(d.s1(true));
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		System.out.println(d.s2(true));
		endTime = System.currentTimeMillis();
		System.out.println("Day " + d.day + " run 1 took " + timeS1 + " milliseconds, run 2 took "
				+ (endTime - startTime) + " milliseconds");

	}

	public long s1(boolean b) {
		List<String> sept = Arrays.asList(getInput(true).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		List<String> jui = Arrays.asList(getInput(false).trim().split("\n")).stream().map(s -> s.trim())
				.collect(Collectors.toList());
		List<Unite> uj=new ArrayList<>();
		List<Unite> us=new ArrayList<>();
		uj.addAll(jui.stream().map(s->s.split(";", -1)).map(Unite::new).collect(Collectors.toList()));
		us.addAll(sept.stream().map(s->s.split(";", -1)).map(Unite::new).collect(Collectors.toList()));
		System.out.println(uj.stream().filter(u->u.marchand.equals("PU")).count());
		System.out.println(us.stream().filter(u->u.marchand.equals("PU")).count());
		
		return 0L;
	}


	public long s2(boolean b) {

		return 0L;
	}
	
	public class Unite{
		String sirus_id;
		String ind_result;
		String unite_type;
		String ind_entreprise;
		String ind_entreprise_effet_daaaammjj;
		String rs;
		String rs_norm;
		String creat_daaaammjj;
		String nic_siege;
		String adr_depcom;
		String envel_daaaa;
		String stat_etat;
		String stat_etat_effet_daaaammjj;
		String stat_etat_maj_auteur;
		String stat_etat_trt_d;
		String ape;
		String ape_effet_daaaammjj;
		String ape_maj_auteur;
		String ape_trt_d;
		String eff_3112;
		String eff_etp;
		String eff_effet_daaaammjj;
		String eff_maj_auteur;
		String eff_trt_d;
		String eff_moyen;
		String eff_moyen_effet_daaaammjj;
		String eff_moyen_maj_auteur;
		String ca;
		String ca_export;
		String total_bilan;
		String ex_clot_daaaammjj;
		String ex_duree;
		String ca_effet_daaaammjj;
		String ca_maj_auteur;
		String ca_trt_d;
		String categorie;
		String categorie_effet_daaaammjj;
		String categorie_eur;
		String categorie_eur_effet_daaaammjj;
		String exploit;
		String exploit_effet_daaaammjj;
		String product;
		String product_effet_daaaammjj;
		String cj;
		String cj_effet_daaaammjj;
		String cj_maj_auteur;
		String eff_interim;
		String eff_interim_effet_daaaammjj;
		String eff_courant;
		String eff_courant_effet_daaaammjj;
		String ca_courant;
		String ca_courant_effet_daaaammjj;
		String ind_recme;
		String ind_recme_effet_daaaammjj;
		String secteur_instit;
		String secteur_instit_effet_daaaammjj;
		String marchand;
		String marchand_effet_daaaammjj;
		String nbet_a;
		String nbet_a_effet_daaaammjj;
		String cotation;
		String cotation_effet_daaaammjj;
		String esane_ch_appart_ind;
		String denom_sirene;
		String denom_condense_sirene;
		String nom_sirene;
		String nom_usage_sirene;
		String prenom1_sirene;
		String sigle_sirene;
		String adr_loc_geo_depcom;
		String adr_voie_num;
		String adr_voie_repet;
		String adr_voie_type;
		String adr_voie_lib;
		String adr_compl;
		String adr_cedex;
		String adr_codpost;
		String adr_distsp;
		String adr_com_lib;
		String adr_l1;
		String adr_l2;
		String adr_l3;
		String adr_l4;
		String adr_l5;
		String adr_l6;
		String adr_l7;
		
		public Unite(String sirus_id, String ind_result, String unite_type, String ind_entreprise,
				String ind_entreprise_effet_daaaammjj, String rs, String rs_norm, String creat_daaaammjj,
				String nic_siege, String adr_depcom, String envel_daaaa, String stat_etat,
				String stat_etat_effet_daaaammjj, String stat_etat_maj_auteur, String stat_etat_trt_d, String ape,
				String ape_effet_daaaammjj, String ape_maj_auteur, String ape_trt_d, String eff_3112, String eff_etp,
				String eff_effet_daaaammjj, String eff_maj_auteur, String eff_trt_d, String eff_moyen,
				String eff_moyen_effet_daaaammjj, String eff_moyen_maj_auteur, String ca, String ca_export,
				String total_bilan, String ex_clot_daaaammjj, String ex_duree, String ca_effet_daaaammjj,
				String ca_maj_auteur, String ca_trt_d, String categorie, String categorie_effet_daaaammjj,
				String categorie_eur, String categorie_eur_effet_daaaammjj, String exploit,
				String exploit_effet_daaaammjj, String product, String product_effet_daaaammjj, String cj,
				String cj_effet_daaaammjj, String cj_maj_auteur, String eff_interim, String eff_interim_effet_daaaammjj,
				String eff_courant, String eff_courant_effet_daaaammjj, String ca_courant,
				String ca_courant_effet_daaaammjj, String ind_recme, String ind_recme_effet_daaaammjj,
				String secteur_instit, String secteur_instit_effet_daaaammjj, String marchand,
				String marchand_effet_daaaammjj, String nbet_a, String nbet_a_effet_daaaammjj, String cotation,
				String cotation_effet_daaaammjj, String esane_ch_appart_ind, String denom_sirene,
				String denom_condense_sirene, String nom_sirene, String nom_usage_sirene, String prenom1_sirene,
				String sigle_sirene, String adr_loc_geo_depcom, String adr_voie_num, String adr_voie_repet,
				String adr_voie_type, String adr_voie_lib, String adr_compl, String adr_cedex, String adr_codpost,
				String adr_distsp, String adr_com_lib, String adr_l1, String adr_l2, String adr_l3, String adr_l4,
				String adr_l5, String adr_l6, String adr_l7) {
			super();
			this.sirus_id = sirus_id;
			this.ind_result = ind_result;
			this.unite_type = unite_type;
			this.ind_entreprise = ind_entreprise;
			this.ind_entreprise_effet_daaaammjj = ind_entreprise_effet_daaaammjj;
			this.rs = rs;
			this.rs_norm = rs_norm;
			this.creat_daaaammjj = creat_daaaammjj;
			this.nic_siege = nic_siege;
			this.adr_depcom = adr_depcom;
			this.envel_daaaa = envel_daaaa;
			this.stat_etat = stat_etat;
			this.stat_etat_effet_daaaammjj = stat_etat_effet_daaaammjj;
			this.stat_etat_maj_auteur = stat_etat_maj_auteur;
			this.stat_etat_trt_d = stat_etat_trt_d;
			this.ape = ape;
			this.ape_effet_daaaammjj = ape_effet_daaaammjj;
			this.ape_maj_auteur = ape_maj_auteur;
			this.ape_trt_d = ape_trt_d;
			this.eff_3112 = eff_3112;
			this.eff_etp = eff_etp;
			this.eff_effet_daaaammjj = eff_effet_daaaammjj;
			this.eff_maj_auteur = eff_maj_auteur;
			this.eff_trt_d = eff_trt_d;
			this.eff_moyen = eff_moyen;
			this.eff_moyen_effet_daaaammjj = eff_moyen_effet_daaaammjj;
			this.eff_moyen_maj_auteur = eff_moyen_maj_auteur;
			this.ca = ca;
			this.ca_export = ca_export;
			this.total_bilan = total_bilan;
			this.ex_clot_daaaammjj = ex_clot_daaaammjj;
			this.ex_duree = ex_duree;
			this.ca_effet_daaaammjj = ca_effet_daaaammjj;
			this.ca_maj_auteur = ca_maj_auteur;
			this.ca_trt_d = ca_trt_d;
			this.categorie = categorie;
			this.categorie_effet_daaaammjj = categorie_effet_daaaammjj;
			this.categorie_eur = categorie_eur;
			this.categorie_eur_effet_daaaammjj = categorie_eur_effet_daaaammjj;
			this.exploit = exploit;
			this.exploit_effet_daaaammjj = exploit_effet_daaaammjj;
			this.product = product;
			this.product_effet_daaaammjj = product_effet_daaaammjj;
			this.cj = cj;
			this.cj_effet_daaaammjj = cj_effet_daaaammjj;
			this.cj_maj_auteur = cj_maj_auteur;
			this.eff_interim = eff_interim;
			this.eff_interim_effet_daaaammjj = eff_interim_effet_daaaammjj;
			this.eff_courant = eff_courant;
			this.eff_courant_effet_daaaammjj = eff_courant_effet_daaaammjj;
			this.ca_courant = ca_courant;
			this.ca_courant_effet_daaaammjj = ca_courant_effet_daaaammjj;
			this.ind_recme = ind_recme;
			this.ind_recme_effet_daaaammjj = ind_recme_effet_daaaammjj;
			this.secteur_instit = secteur_instit;
			this.secteur_instit_effet_daaaammjj = secteur_instit_effet_daaaammjj;
			this.marchand = marchand;
			this.marchand_effet_daaaammjj = marchand_effet_daaaammjj;
			this.nbet_a = nbet_a;
			this.nbet_a_effet_daaaammjj = nbet_a_effet_daaaammjj;
			this.cotation = cotation;
			this.cotation_effet_daaaammjj = cotation_effet_daaaammjj;
			this.esane_ch_appart_ind = esane_ch_appart_ind;
			this.denom_sirene = denom_sirene;
			this.denom_condense_sirene = denom_condense_sirene;
			this.nom_sirene = nom_sirene;
			this.nom_usage_sirene = nom_usage_sirene;
			this.prenom1_sirene = prenom1_sirene;
			this.sigle_sirene = sigle_sirene;
			this.adr_loc_geo_depcom = adr_loc_geo_depcom;
			this.adr_voie_num = adr_voie_num;
			this.adr_voie_repet = adr_voie_repet;
			this.adr_voie_type = adr_voie_type;
			this.adr_voie_lib = adr_voie_lib;
			this.adr_compl = adr_compl;
			this.adr_cedex = adr_cedex;
			this.adr_codpost = adr_codpost;
			this.adr_distsp = adr_distsp;
			this.adr_com_lib = adr_com_lib;
			this.adr_l1 = adr_l1;
			this.adr_l2 = adr_l2;
			this.adr_l3 = adr_l3;
			this.adr_l4 = adr_l4;
			this.adr_l5 = adr_l5;
			this.adr_l6 = adr_l6;
			this.adr_l7 = adr_l7;
		}
		public Unite(String[] data) {
			super();
			this.sirus_id = data[0];
			this.ind_result = data[1];
			this.unite_type = data[2];
			this.ind_entreprise = data[3];
			this.ind_entreprise_effet_daaaammjj = data[4];
			this.rs = data[5];
			this.rs_norm = data[6];
			this.creat_daaaammjj = data[7];
			this.nic_siege =data[8];
			this.adr_depcom = data[9];
			this.envel_daaaa = data[10];
			this.stat_etat = data[11];
			this.stat_etat_effet_daaaammjj = data[12];
			this.stat_etat_maj_auteur = data[13];
			this.stat_etat_trt_d = data[14];
			this.ape = data[15];
			this.ape_effet_daaaammjj = data[16];
			this.ape_maj_auteur =data[17];
			this.ape_trt_d = data[18];
			this.eff_3112 =data[19];
			this.eff_etp = data[20];
			this.eff_effet_daaaammjj = data[21];
			this.eff_maj_auteur = data[22];
			this.eff_trt_d = data[23];
			this.eff_moyen = data[24];
			this.eff_moyen_effet_daaaammjj = data[25];
			this.eff_moyen_maj_auteur = data[26];
			this.ca = data[27];
			this.ca_export =  data[28];
			this.total_bilan =data[29];
			this.ex_clot_daaaammjj = data[30];
			this.ex_duree = data[31];
			this.ca_effet_daaaammjj =data[32];
			this.ca_maj_auteur = data[33];
			this.ca_trt_d = data[34];
			this.categorie =data[35];
			this.categorie_effet_daaaammjj = data[36];
			this.categorie_eur = data[37];
			this.categorie_eur_effet_daaaammjj =data[38];
			this.exploit = data[39];
			this.exploit_effet_daaaammjj = data[40];
			this.product = data[41];
			this.product_effet_daaaammjj = data[42];
			this.cj = data[43];
			this.cj_effet_daaaammjj = data[44];
			this.cj_maj_auteur =data[45];
			this.eff_interim =  data[46];
			this.eff_interim_effet_daaaammjj =data[47];
			this.eff_courant = data[48];
			this.eff_courant_effet_daaaammjj =data[49];
			this.ca_courant = data[50];
			this.ca_courant_effet_daaaammjj = data[51];
			this.ind_recme = data[52];
			this.ind_recme_effet_daaaammjj =  data[53];
			this.secteur_instit = data[54];
			this.secteur_instit_effet_daaaammjj = data[55];
			this.marchand = data[56];
			this.marchand_effet_daaaammjj =data[57];
			this.nbet_a = data[58];
			this.nbet_a_effet_daaaammjj = data[59];
			this.cotation = data[60];
			this.cotation_effet_daaaammjj = data[61];
			this.esane_ch_appart_ind = data[62];
			this.denom_sirene = data[63];
			this.denom_condense_sirene = data[64];
			this.nom_sirene = data[65];
			this.nom_usage_sirene = data[66];
			this.prenom1_sirene = data[67];
			this.sigle_sirene = data[68];
			this.adr_loc_geo_depcom = data[69];
			this.adr_voie_num = data[70];
			this.adr_voie_repet=data[71];
			this.adr_voie_type= data[72];
			this.adr_voie_lib = data[73];
			this.adr_compl =    data[74];
			this.adr_cedex =    data[75];
			this.adr_codpost =  data[76];
			this.adr_distsp =   data[77];
			this.adr_com_lib =  data[78];
			this.adr_l1 = data[79];
			this.adr_l2 = data[80];
			this.adr_l3 = data[81];
			this.adr_l4 = data[82];
			this.adr_l5 = data[83];
			this.adr_l6 = data[84];
			this.adr_l7 = data[85];
		}
		public String getSirus_id() {
			return sirus_id;
		}
		public void setSirus_id(String sirus_id) {
			this.sirus_id = sirus_id;
		}
		public String getInd_result() {
			return ind_result;
		}
		public void setInd_result(String ind_result) {
			this.ind_result = ind_result;
		}
		public String getUnite_type() {
			return unite_type;
		}
		public void setUnite_type(String unite_type) {
			this.unite_type = unite_type;
		}
		public String getInd_entreprise() {
			return ind_entreprise;
		}
		public void setInd_entreprise(String ind_entreprise) {
			this.ind_entreprise = ind_entreprise;
		}
		public String getInd_entreprise_effet_daaaammjj() {
			return ind_entreprise_effet_daaaammjj;
		}
		public void setInd_entreprise_effet_daaaammjj(String ind_entreprise_effet_daaaammjj) {
			this.ind_entreprise_effet_daaaammjj = ind_entreprise_effet_daaaammjj;
		}
		public String getRs() {
			return rs;
		}
		public void setRs(String rs) {
			this.rs = rs;
		}
		public String getRs_norm() {
			return rs_norm;
		}
		public void setRs_norm(String rs_norm) {
			this.rs_norm = rs_norm;
		}
		public String getCreat_daaaammjj() {
			return creat_daaaammjj;
		}
		public void setCreat_daaaammjj(String creat_daaaammjj) {
			this.creat_daaaammjj = creat_daaaammjj;
		}
		public String getNic_siege() {
			return nic_siege;
		}
		public void setNic_siege(String nic_siege) {
			this.nic_siege = nic_siege;
		}
		public String getAdr_depcom() {
			return adr_depcom;
		}
		public void setAdr_depcom(String adr_depcom) {
			this.adr_depcom = adr_depcom;
		}
		public String getEnvel_daaaa() {
			return envel_daaaa;
		}
		public void setEnvel_daaaa(String envel_daaaa) {
			this.envel_daaaa = envel_daaaa;
		}
		public String getStat_etat() {
			return stat_etat;
		}
		public void setStat_etat(String stat_etat) {
			this.stat_etat = stat_etat;
		}
		public String getStat_etat_effet_daaaammjj() {
			return stat_etat_effet_daaaammjj;
		}
		public void setStat_etat_effet_daaaammjj(String stat_etat_effet_daaaammjj) {
			this.stat_etat_effet_daaaammjj = stat_etat_effet_daaaammjj;
		}
		public String getStat_etat_maj_auteur() {
			return stat_etat_maj_auteur;
		}
		public void setStat_etat_maj_auteur(String stat_etat_maj_auteur) {
			this.stat_etat_maj_auteur = stat_etat_maj_auteur;
		}
		public String getStat_etat_trt_d() {
			return stat_etat_trt_d;
		}
		public void setStat_etat_trt_d(String stat_etat_trt_d) {
			this.stat_etat_trt_d = stat_etat_trt_d;
		}
		public String getApe() {
			return ape;
		}
		public void setApe(String ape) {
			this.ape = ape;
		}
		public String getApe_effet_daaaammjj() {
			return ape_effet_daaaammjj;
		}
		public void setApe_effet_daaaammjj(String ape_effet_daaaammjj) {
			this.ape_effet_daaaammjj = ape_effet_daaaammjj;
		}
		public String getApe_maj_auteur() {
			return ape_maj_auteur;
		}
		public void setApe_maj_auteur(String ape_maj_auteur) {
			this.ape_maj_auteur = ape_maj_auteur;
		}
		public String getApe_trt_d() {
			return ape_trt_d;
		}
		public void setApe_trt_d(String ape_trt_d) {
			this.ape_trt_d = ape_trt_d;
		}
		public String getEff_3112() {
			return eff_3112;
		}
		public void setEff_3112(String eff_3112) {
			this.eff_3112 = eff_3112;
		}
		public String getEff_etp() {
			return eff_etp;
		}
		public void setEff_etp(String eff_etp) {
			this.eff_etp = eff_etp;
		}
		public String getEff_effet_daaaammjj() {
			return eff_effet_daaaammjj;
		}
		public void setEff_effet_daaaammjj(String eff_effet_daaaammjj) {
			this.eff_effet_daaaammjj = eff_effet_daaaammjj;
		}
		public String getEff_maj_auteur() {
			return eff_maj_auteur;
		}
		public void setEff_maj_auteur(String eff_maj_auteur) {
			this.eff_maj_auteur = eff_maj_auteur;
		}
		public String getEff_trt_d() {
			return eff_trt_d;
		}
		public void setEff_trt_d(String eff_trt_d) {
			this.eff_trt_d = eff_trt_d;
		}
		public String getEff_moyen() {
			return eff_moyen;
		}
		public void setEff_moyen(String eff_moyen) {
			this.eff_moyen = eff_moyen;
		}
		public String getEff_moyen_effet_daaaammjj() {
			return eff_moyen_effet_daaaammjj;
		}
		public void setEff_moyen_effet_daaaammjj(String eff_moyen_effet_daaaammjj) {
			this.eff_moyen_effet_daaaammjj = eff_moyen_effet_daaaammjj;
		}
		public String getEff_moyen_maj_auteur() {
			return eff_moyen_maj_auteur;
		}
		public void setEff_moyen_maj_auteur(String eff_moyen_maj_auteur) {
			this.eff_moyen_maj_auteur = eff_moyen_maj_auteur;
		}
		public String getCa() {
			return ca;
		}
		public void setCa(String ca) {
			this.ca = ca;
		}
		public String getCa_export() {
			return ca_export;
		}
		public void setCa_export(String ca_export) {
			this.ca_export = ca_export;
		}
		public String getTotal_bilan() {
			return total_bilan;
		}
		public void setTotal_bilan(String total_bilan) {
			this.total_bilan = total_bilan;
		}
		public String getEx_clot_daaaammjj() {
			return ex_clot_daaaammjj;
		}
		public void setEx_clot_daaaammjj(String ex_clot_daaaammjj) {
			this.ex_clot_daaaammjj = ex_clot_daaaammjj;
		}
		public String getEx_duree() {
			return ex_duree;
		}
		public void setEx_duree(String ex_duree) {
			this.ex_duree = ex_duree;
		}
		public String getCa_effet_daaaammjj() {
			return ca_effet_daaaammjj;
		}
		public void setCa_effet_daaaammjj(String ca_effet_daaaammjj) {
			this.ca_effet_daaaammjj = ca_effet_daaaammjj;
		}
		public String getCa_maj_auteur() {
			return ca_maj_auteur;
		}
		public void setCa_maj_auteur(String ca_maj_auteur) {
			this.ca_maj_auteur = ca_maj_auteur;
		}
		public String getCa_trt_d() {
			return ca_trt_d;
		}
		public void setCa_trt_d(String ca_trt_d) {
			this.ca_trt_d = ca_trt_d;
		}
		public String getCategorie() {
			return categorie;
		}
		public void setCategorie(String categorie) {
			this.categorie = categorie;
		}
		public String getCategorie_effet_daaaammjj() {
			return categorie_effet_daaaammjj;
		}
		public void setCategorie_effet_daaaammjj(String categorie_effet_daaaammjj) {
			this.categorie_effet_daaaammjj = categorie_effet_daaaammjj;
		}
		public String getCategorie_eur() {
			return categorie_eur;
		}
		public void setCategorie_eur(String categorie_eur) {
			this.categorie_eur = categorie_eur;
		}
		public String getCategorie_eur_effet_daaaammjj() {
			return categorie_eur_effet_daaaammjj;
		}
		public void setCategorie_eur_effet_daaaammjj(String categorie_eur_effet_daaaammjj) {
			this.categorie_eur_effet_daaaammjj = categorie_eur_effet_daaaammjj;
		}
		public String getExploit() {
			return exploit;
		}
		public void setExploit(String exploit) {
			this.exploit = exploit;
		}
		public String getExploit_effet_daaaammjj() {
			return exploit_effet_daaaammjj;
		}
		public void setExploit_effet_daaaammjj(String exploit_effet_daaaammjj) {
			this.exploit_effet_daaaammjj = exploit_effet_daaaammjj;
		}
		public String getProduct() {
			return product;
		}
		public void setProduct(String product) {
			this.product = product;
		}
		public String getProduct_effet_daaaammjj() {
			return product_effet_daaaammjj;
		}
		public void setProduct_effet_daaaammjj(String product_effet_daaaammjj) {
			this.product_effet_daaaammjj = product_effet_daaaammjj;
		}
		public String getCj() {
			return cj;
		}
		public void setCj(String cj) {
			this.cj = cj;
		}
		public String getCj_effet_daaaammjj() {
			return cj_effet_daaaammjj;
		}
		public void setCj_effet_daaaammjj(String cj_effet_daaaammjj) {
			this.cj_effet_daaaammjj = cj_effet_daaaammjj;
		}
		public String getCj_maj_auteur() {
			return cj_maj_auteur;
		}
		public void setCj_maj_auteur(String cj_maj_auteur) {
			this.cj_maj_auteur = cj_maj_auteur;
		}
		public String getEff_interim() {
			return eff_interim;
		}
		public void setEff_interim(String eff_interim) {
			this.eff_interim = eff_interim;
		}
		public String getEff_interim_effet_daaaammjj() {
			return eff_interim_effet_daaaammjj;
		}
		public void setEff_interim_effet_daaaammjj(String eff_interim_effet_daaaammjj) {
			this.eff_interim_effet_daaaammjj = eff_interim_effet_daaaammjj;
		}
		public String getEff_courant() {
			return eff_courant;
		}
		public void setEff_courant(String eff_courant) {
			this.eff_courant = eff_courant;
		}
		public String getEff_courant_effet_daaaammjj() {
			return eff_courant_effet_daaaammjj;
		}
		public void setEff_courant_effet_daaaammjj(String eff_courant_effet_daaaammjj) {
			this.eff_courant_effet_daaaammjj = eff_courant_effet_daaaammjj;
		}
		public String getCa_courant() {
			return ca_courant;
		}
		public void setCa_courant(String ca_courant) {
			this.ca_courant = ca_courant;
		}
		public String getCa_courant_effet_daaaammjj() {
			return ca_courant_effet_daaaammjj;
		}
		public void setCa_courant_effet_daaaammjj(String ca_courant_effet_daaaammjj) {
			this.ca_courant_effet_daaaammjj = ca_courant_effet_daaaammjj;
		}
		public String getInd_recme() {
			return ind_recme;
		}
		public void setInd_recme(String ind_recme) {
			this.ind_recme = ind_recme;
		}
		public String getInd_recme_effet_daaaammjj() {
			return ind_recme_effet_daaaammjj;
		}
		public void setInd_recme_effet_daaaammjj(String ind_recme_effet_daaaammjj) {
			this.ind_recme_effet_daaaammjj = ind_recme_effet_daaaammjj;
		}
		public String getSecteur_instit() {
			return secteur_instit;
		}
		public void setSecteur_instit(String secteur_instit) {
			this.secteur_instit = secteur_instit;
		}
		public String getSecteur_instit_effet_daaaammjj() {
			return secteur_instit_effet_daaaammjj;
		}
		public void setSecteur_instit_effet_daaaammjj(String secteur_instit_effet_daaaammjj) {
			this.secteur_instit_effet_daaaammjj = secteur_instit_effet_daaaammjj;
		}
		public String getMarchand() {
			return marchand;
		}
		public void setMarchand(String marchand) {
			this.marchand = marchand;
		}
		public String getMarchand_effet_daaaammjj() {
			return marchand_effet_daaaammjj;
		}
		public void setMarchand_effet_daaaammjj(String marchand_effet_daaaammjj) {
			this.marchand_effet_daaaammjj = marchand_effet_daaaammjj;
		}
		public String getNbet_a() {
			return nbet_a;
		}
		public void setNbet_a(String nbet_a) {
			this.nbet_a = nbet_a;
		}
		public String getNbet_a_effet_daaaammjj() {
			return nbet_a_effet_daaaammjj;
		}
		public void setNbet_a_effet_daaaammjj(String nbet_a_effet_daaaammjj) {
			this.nbet_a_effet_daaaammjj = nbet_a_effet_daaaammjj;
		}
		public String getCotation() {
			return cotation;
		}
		public void setCotation(String cotation) {
			this.cotation = cotation;
		}
		public String getCotation_effet_daaaammjj() {
			return cotation_effet_daaaammjj;
		}
		public void setCotation_effet_daaaammjj(String cotation_effet_daaaammjj) {
			this.cotation_effet_daaaammjj = cotation_effet_daaaammjj;
		}
		public String getEsane_ch_appart_ind() {
			return esane_ch_appart_ind;
		}
		public void setEsane_ch_appart_ind(String esane_ch_appart_ind) {
			this.esane_ch_appart_ind = esane_ch_appart_ind;
		}
		public String getDenom_sirene() {
			return denom_sirene;
		}
		public void setDenom_sirene(String denom_sirene) {
			this.denom_sirene = denom_sirene;
		}
		public String getDenom_condense_sirene() {
			return denom_condense_sirene;
		}
		public void setDenom_condense_sirene(String denom_condense_sirene) {
			this.denom_condense_sirene = denom_condense_sirene;
		}
		public String getNom_sirene() {
			return nom_sirene;
		}
		public void setNom_sirene(String nom_sirene) {
			this.nom_sirene = nom_sirene;
		}
		public String getNom_usage_sirene() {
			return nom_usage_sirene;
		}
		public void setNom_usage_sirene(String nom_usage_sirene) {
			this.nom_usage_sirene = nom_usage_sirene;
		}
		public String getPrenom1_sirene() {
			return prenom1_sirene;
		}
		public void setPrenom1_sirene(String prenom1_sirene) {
			this.prenom1_sirene = prenom1_sirene;
		}
		public String getSigle_sirene() {
			return sigle_sirene;
		}
		public void setSigle_sirene(String sigle_sirene) {
			this.sigle_sirene = sigle_sirene;
		}
		public String getAdr_loc_geo_depcom() {
			return adr_loc_geo_depcom;
		}
		public void setAdr_loc_geo_depcom(String adr_loc_geo_depcom) {
			this.adr_loc_geo_depcom = adr_loc_geo_depcom;
		}
		public String getAdr_voie_num() {
			return adr_voie_num;
		}
		public void setAdr_voie_num(String adr_voie_num) {
			this.adr_voie_num = adr_voie_num;
		}
		public String getAdr_voie_repet() {
			return adr_voie_repet;
		}
		public void setAdr_voie_repet(String adr_voie_repet) {
			this.adr_voie_repet = adr_voie_repet;
		}
		public String getAdr_voie_type() {
			return adr_voie_type;
		}
		public void setAdr_voie_type(String adr_voie_type) {
			this.adr_voie_type = adr_voie_type;
		}
		public String getAdr_voie_lib() {
			return adr_voie_lib;
		}
		public void setAdr_voie_lib(String adr_voie_lib) {
			this.adr_voie_lib = adr_voie_lib;
		}
		public String getAdr_compl() {
			return adr_compl;
		}
		public void setAdr_compl(String adr_compl) {
			this.adr_compl = adr_compl;
		}
		public String getAdr_cedex() {
			return adr_cedex;
		}
		public void setAdr_cedex(String adr_cedex) {
			this.adr_cedex = adr_cedex;
		}
		public String getAdr_codpost() {
			return adr_codpost;
		}
		public void setAdr_codpost(String adr_codpost) {
			this.adr_codpost = adr_codpost;
		}
		public String getAdr_distsp() {
			return adr_distsp;
		}
		public void setAdr_distsp(String adr_distsp) {
			this.adr_distsp = adr_distsp;
		}
		public String getAdr_com_lib() {
			return adr_com_lib;
		}
		public void setAdr_com_lib(String adr_com_lib) {
			this.adr_com_lib = adr_com_lib;
		}
		public String getAdr_l1() {
			return adr_l1;
		}
		public void setAdr_l1(String adr_l1) {
			this.adr_l1 = adr_l1;
		}
		public String getAdr_l2() {
			return adr_l2;
		}
		public void setAdr_l2(String adr_l2) {
			this.adr_l2 = adr_l2;
		}
		public String getAdr_l3() {
			return adr_l3;
		}
		public void setAdr_l3(String adr_l3) {
			this.adr_l3 = adr_l3;
		}
		public String getAdr_l4() {
			return adr_l4;
		}
		public void setAdr_l4(String adr_l4) {
			this.adr_l4 = adr_l4;
		}
		public String getAdr_l5() {
			return adr_l5;
		}
		public void setAdr_l5(String adr_l5) {
			this.adr_l5 = adr_l5;
		}
		public String getAdr_l6() {
			return adr_l6;
		}
		public void setAdr_l6(String adr_l6) {
			this.adr_l6 = adr_l6;
		}
		public String getAdr_l7() {
			return adr_l7;
		}
		public void setAdr_l7(String adr_l7) {
			this.adr_l7 = adr_l7;
		}
		@Override
		public String toString() {
			return "Unite [sirus_id=" + sirus_id + ", ind_result=" + ind_result + ", unite_type=" + unite_type
					+ ", ind_entreprise=" + ind_entreprise + ", ind_entreprise_effet_daaaammjj="
					+ ind_entreprise_effet_daaaammjj + ", rs=" + rs + ", rs_norm=" + rs_norm + ", creat_daaaammjj="
					+ creat_daaaammjj + ", nic_siege=" + nic_siege + ", adr_depcom=" + adr_depcom + ", envel_daaaa="
					+ envel_daaaa + ", stat_etat=" + stat_etat + ", stat_etat_effet_daaaammjj="
					+ stat_etat_effet_daaaammjj + ", stat_etat_maj_auteur=" + stat_etat_maj_auteur
					+ ", stat_etat_trt_d=" + stat_etat_trt_d + ", ape=" + ape + ", ape_effet_daaaammjj="
					+ ape_effet_daaaammjj + ", ape_maj_auteur=" + ape_maj_auteur + ", ape_trt_d=" + ape_trt_d
					+ ", eff_3112=" + eff_3112 + ", eff_etp=" + eff_etp + ", eff_effet_daaaammjj=" + eff_effet_daaaammjj
					+ ", eff_maj_auteur=" + eff_maj_auteur + ", eff_trt_d=" + eff_trt_d + ", eff_moyen=" + eff_moyen
					+ ", eff_moyen_effet_daaaammjj=" + eff_moyen_effet_daaaammjj + ", eff_moyen_maj_auteur="
					+ eff_moyen_maj_auteur + ", ca=" + ca + ", ca_export=" + ca_export + ", total_bilan=" + total_bilan
					+ ", ex_clot_daaaammjj=" + ex_clot_daaaammjj + ", ex_duree=" + ex_duree + ", ca_effet_daaaammjj="
					+ ca_effet_daaaammjj + ", ca_maj_auteur=" + ca_maj_auteur + ", ca_trt_d=" + ca_trt_d
					+ ", categorie=" + categorie + ", categorie_effet_daaaammjj=" + categorie_effet_daaaammjj
					+ ", categorie_eur=" + categorie_eur + ", categorie_eur_effet_daaaammjj="
					+ categorie_eur_effet_daaaammjj + ", exploit=" + exploit + ", exploit_effet_daaaammjj="
					+ exploit_effet_daaaammjj + ", product=" + product + ", product_effet_daaaammjj="
					+ product_effet_daaaammjj + ", cj=" + cj + ", cj_effet_daaaammjj=" + cj_effet_daaaammjj
					+ ", cj_maj_auteur=" + cj_maj_auteur + ", eff_interim=" + eff_interim
					+ ", eff_interim_effet_daaaammjj=" + eff_interim_effet_daaaammjj + ", eff_courant=" + eff_courant
					+ ", eff_courant_effet_daaaammjj=" + eff_courant_effet_daaaammjj + ", ca_courant=" + ca_courant
					+ ", ca_courant_effet_daaaammjj=" + ca_courant_effet_daaaammjj + ", ind_recme=" + ind_recme
					+ ", ind_recme_effet_daaaammjj=" + ind_recme_effet_daaaammjj + ", secteur_instit=" + secteur_instit
					+ ", secteur_instit_effet_daaaammjj=" + secteur_instit_effet_daaaammjj + ", marchand=" + marchand
					+ ", marchand_effet_daaaammjj=" + marchand_effet_daaaammjj + ", nbet_a=" + nbet_a
					+ ", nbet_a_effet_daaaammjj=" + nbet_a_effet_daaaammjj + ", cotation=" + cotation
					+ ", cotation_effet_daaaammjj=" + cotation_effet_daaaammjj + ", esane_ch_appart_ind="
					+ esane_ch_appart_ind + ", denom_sirene=" + denom_sirene + ", denom_condense_sirene="
					+ denom_condense_sirene + ", nom_sirene=" + nom_sirene + ", nom_usage_sirene=" + nom_usage_sirene
					+ ", prenom1_sirene=" + prenom1_sirene + ", sigle_sirene=" + sigle_sirene + ", adr_loc_geo_depcom="
					+ adr_loc_geo_depcom + ", adr_voie_num=" + adr_voie_num + ", adr_voie_repet=" + adr_voie_repet
					+ ", adr_voie_type=" + adr_voie_type + ", adr_voie_lib=" + adr_voie_lib + ", adr_compl=" + adr_compl
					+ ", adr_cedex=" + adr_cedex + ", adr_codpost=" + adr_codpost + ", adr_distsp=" + adr_distsp
					+ ", adr_com_lib=" + adr_com_lib + ", adr_l1=" + adr_l1 + ", adr_l2=" + adr_l2 + ", adr_l3="
					+ adr_l3 + ", adr_l4=" + adr_l4 + ", adr_l5=" + adr_l5 + ", adr_l6=" + adr_l6 + ", adr_l7=" + adr_l7
					+ "]";
		}
		
	}
	
	public static List<Long> getDuration() {
		A2016Day11 d = new A2016Day11(11);
		long startTime = System.currentTimeMillis();
		d.s1(true);
		long endTime = System.currentTimeMillis();
		long timeS1 = endTime - startTime;
		startTime = System.currentTimeMillis();
		d.s2(true);
		endTime = System.currentTimeMillis();
		return Arrays.asList(timeS1, endTime - startTime);
	}

}
