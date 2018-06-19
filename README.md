Derek Nishimoto
Compatibility Predictor

This program receives input from a json file and generates an output json file. The input file contains data about applicants and team members, and generates an output.json which contains each applicants predicted compatibility with the team. Scoring of applicants is determined by the compareApplicants function, which computes the differences between each applicants attributes and the teams averages. It then takes that difference, subtracts it from the maximun point value of 10, and then divides by 10, resulting in the applicants final score. The applicants names and scores are then written to an existing output.json file or creates a new one.
