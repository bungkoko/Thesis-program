/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticQA.models.ontology;


import java.net.URISyntaxException;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.Reasoner.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.OWLOntologyMerger;

import SemanticQA.constant.Ontology;

/**
 *
 * @author syamsul
 */
public abstract class OntologyLoader {
	
	protected OWLOntology ontology;
	protected OWLReasoner reasoner;
	protected OWLDataFactory dataFactory;
	
	public OntologyLoader() {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		dataFactory = manager.getOWLDataFactory();
		
		try {
			manager.loadOntologyFromOntologyDocument(IRI.create(this.getClass().getClassLoader().getResource("ntbgov.owl")));
//			manager.loadOntologyFromOntologyDocument(IRI.create("file:///home/syamsul/Documents/Thesis-program/Ontologi/ntbpar.owl"));
//			manager.loadOntologyFromOntologyDocument(IRI.create("file:///home/syamsul/Documents/Thesis-program/Ontologi/ntbgov.owl"));
//			manager.loadOntologyFromOntologyDocument(IRI.create("file:///home/syamsul/Documents/Thesis-program/Ontologi/ntbgeo.owl"));
			
			OWLOntologyMerger merger = new OWLOntologyMerger(manager);
			ontology = merger.createMergedOntology(manager, IRI.create(Ontology.ONTO_MERGED_URI));
			
			ReasonerFactory rf = new Reasoner.ReasonerFactory();
			
			reasoner = rf.createReasoner(ontology, new SimpleConfiguration());
			reasoner.precomputeInferences(InferenceType.CLASS_ASSERTIONS);
			reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
			
		} catch (OWLOntologyCreationException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
